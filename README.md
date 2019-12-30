# Kotlin Spring Boot 프로젝트에 Arrow를 최대한 적용해보기

[Kotlin Realworld API](https://github.com/istonikula/realworld-api) 프로젝트를 참고해서 Arrow를 Spring에 어떻게 적용해볼지 생각해보기

## 예제 살펴보기

## 순수한 영역과 비순수한 영역을 구분하기

비즈니스 영역(Domain, Use Case)과 나머지 Framework, Infra, .. 등을 분리하고 비즈니스 영역을 순수하게 유지한다.

[Clean 아키텍처](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[Onion 아키텍처](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/)
[Hexagonal 아키텍처)(http://www.dossier-andreas.net/software_architecture/ports_and_adapters.html)

## 예제에서 어느 부분?

`User.kt`와 `UserService.kt`는 순수해야한다

## 예제에서 순수하지 않은 부분

### Throw를 이용한 에러 처리

#### 도메인 유효성 검사에서 예외

```kotlin
data class Userid private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Userid {
            if (value.length > 16) throw UseridLimitExceeded(value)
            return Userid(value)
        }
    }
}

data class Username private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Username {
            if (value.length > 80) throw UsernameLimitExceeded(value)
            return Username(value)
        }
    }
}
```

#### 데이터베이스에서 예외 

```kotlin
@Repository
class UserRepositoryImpl(
        private val mongoTemplate: MongoTemplate
): UserRepository {
    override fun create(userDocument: UserDocument): UserDocument =
            try {
                mongoTemplate.insert(userDocument)
            } catch (e: DuplicateKeyException) {
                throw DuplicateUser(userDocument.id)
            }

    override fun get(id: String): UserDocument =
            mongoTemplate.findById(id, UserDocument::class.java) ?: throw UserNotFound(id)
}
```

### 데이터베이스 접근을 위한 함수 사용

```kotlin
@Repository
class UserRepositoryImpl(
        private val mongoTemplate: MongoTemplate
): UserRepository {
    override fun create(userDocument: UserDocument): UserDocument =
            try {
                mongoTemplate.insert(userDocument)
            } catch (e: DuplicateKeyException) {
                throw DuplicateUser(userDocument.id)
            }

    override fun get(id: String): UserDocument =
            mongoTemplate.findById(id, UserDocument::class.java) ?: throw UserNotFound(id)
}
```

## 예외 대신 Either로 순수하게 만들기

### Arrow Either

https://arrow-kt.io/docs/apidocs/arrow-core-data/arrow.core/-either/

```Kotlin
val right: Either<String, Int> = Either.Right(5)
val value = right.flatMap{ Either.Right(it + 1) }

val left: Either<String, Int> = Either.Left("Something went wrong")
val value = left.flatMap{ Either.Right(it + 1) }
```

```kotlin
@Repository
class UserRepositoryImpl(
        private val mongoTemplate: MongoTemplate
): UserRepository {
    override fun create(userDocument: UserDocument): Either<UserException, UserDocument> =
            try {
                Right(mongoTemplate.insert(userDocument))
            } catch (e: DuplicateKeyException) {
                Left(DuplicateUser(userDocument.id))
            }

    override fun get(id: String): Either<UserException, UserDocument> =
            mongoTemplate.findById(id, UserDocument::class.java)?.let { Right(it) } ?: Left(UserNotFound(id))
}
```

```kotlin
@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun get(id: String): Either<UserException, UserPayload> {
        return userRepository.get(id).map {
            it.toDomain()
        }.map {
            UserPayload.fromDomain(it)
        }
    }

    fun create(input: CreateUserInput): Either<UserException, UserPayload> {
        val user = input.toDomain()
        val userDocument = UserDocument.fromDomain(user)
        return userRepository.create(userDocument).map {
            it.toDomain()
        }.map {
            UserPayload.fromDomain(it)
        }
    }
}
```

### Effect는 Controller에서

```kotlin
@RestController
@RequestMapping("/v1/users")
class UserController(
        private val userService: UserService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): UserPayload {
        val userPayload = userService.get(id)
        return when(userPayload) {
            is Either.Right -> userPayload.b
            is Either.Left -> throw userPayload.a
        }
    }

    @PostMapping("/")
    fun create(@RequestBody input: CreateUserInput): UserPayload {
        val userPayload = userService.create(input)
        return when(userPayload) {
            is Either.Right -> userPayload.b
            is Either.Left -> throw userPayload.a
        }
    }
}
```

### Arrow Monad Comprehension

```kotlin
Either.fx<Int, Int> {
    val (a) = Either.Right(1)
    val (b) = Either.Right(1 + a)
    val (c) = Either.Right(1 + b)
    a + b + c
}
```

```kotlin
@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun get(id: String): Either<UserException, UserPayload> = Either.fx {
        val (userDocument) = userRepository.get(id)
        val (user) = userDocument.toDomain()
        UserPayload.fromDomain(user)
    }

    fun create(input: CreateUserInput): Either<UserException, UserPayload> = Either.fx {
        val (user) = input.toDomain()
        val userDocument = UserDocument.fromDomain(user)
        val (createdUserDocument) = userRepository.create(userDocument)
        val (createdUser) = createdUserDocument.toDomain()
        UserPayload.fromDomain(createdUser)
    }
}
```

## Effect를 IO로 순수하게 만들기

### Arrow Fx 

https://arrow-kt.io/docs/fx/

```kotlin
@Repository
class UserRepositoryImpl(
        private val mongoTemplate: MongoTemplate
): UserRepository {
    override fun create(userDocument: UserDocument): IO<Either<UserException, UserDocument>> = IO {
        try {
            Right(mongoTemplate.insert(userDocument))
        } catch (e: DuplicateKeyException) {
            Left(DuplicateUser(userDocument.id))
        }
    }

    override fun get(id: String): IO<Either<UserException, UserDocument>> = IO {
        mongoTemplate.findById(id, UserDocument::class.java)?.let { Right(it) } ?: Left(UserNotFound(id))
    }
}
```

### Effect는 Controller에서

```kotlin
val userPayload = userService.get(id).unsafeRunSync()
```

### EitherT

모나드는 합성이 어렵다. 특정 모나드에 적용할 수 있는 모나드 트랜스포머(OptionT, StateT, EitherT ...)

https://arrow-kt.io/docs/arrow/mtl/eithert/

```kotlin
@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun get(id: String): IO<Either<UserException, UserPayload>> =
            EitherT.monad<ForIO, UserException>(IO.monad()).fx.monad {
                val userDocument = EitherT(userRepository.get(id)).bind()
                val user = EitherT(IO.just(userDocument.toDomain())).bind()
                UserPayload.fromDomain(user)
            }.value().fix()


    fun create(input: CreateUserInput): IO<Either<UserException, UserPayload>> =
            EitherT.monad<ForIO, UserException>(IO.monad()).fx.monad {
                val user = EitherT(IO.just(input.toDomain())).bind()
                val userDocument = UserDocument.fromDomain(user)
                val createdUserDocument = EitherT(userRepository.create(userDocument)).bind()
                val createdUser = EitherT(IO.just(createdUserDocument.toDomain())).bind()
                UserPayload.fromDomain(createdUser)
            }.value().fix()
}
```

## 생각해볼 내용

- IO 컨텍스트를 Tagless Final로 유연하게 만들기
