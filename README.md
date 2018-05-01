# zkotlin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/br.com.zup.kotlin/zkotlin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/br.com.zup.kotlin/zkotlin)


Extensions for Kotlin Language

- Either<L, R> - [Examples](src/test/kotlin/br/com/zup/zkotlin/either/EitherTest.kt)
    - Either.Left<L,R> Either.Right<L,R>
        - Either.right<Int, String>("right value")
        - Either.left<Int, String>(-1)
        - Either.rightOf("right value")
        - Either.leftOf(-1)

    - fun isRight(): Boolean
    - fun isLeft(): Boolean
    - fun component1(): L
    - fun component2(): R
    - fun get(): R
    - fun failure(): L 
    - fun <T> success(onSuccess: (R) -> T): T
    - fun <T> failure(onFail: (L) -> T): T
    - fun <T> fold(left: (L) -> T, right: (R) -> T): T
    - fun <TL, TR> foldCompose(left: (L) -> Either<TL, TR>, right: (R) -> Either<TL, TR>): Either<TL, TR>
    - fun <TR> andThen(supplier: (R) -> Either<L, TR>): Either<L, TR>
    - fun getOrElse(supplier: (L) -> Either<L, R>): Either<L, R>
    
- List<Either<L, R>> - [Examples](src/test/kotlin/br/com/zup/zkotlin/either/EitherListTest.kt)
    - fun <L, R, T> List<Either<L, R>>.fold(onError: (L) -> Unit, onSuccess: (R) -> T): List<T>
    - fun <L, R> List<Either<L, R>>.foldSuccess(): List<R>
    - fun <L, R, T> List<Either<L, R>>.foldSuccess(onSuccess: (R) -> T): List<T>
    - fun <L, R> List<Either<L, R>>.foldError(onError: (L) -> Unit): List<Either<L, R>>
    
- Coroutine List - [Examples](src/test/kotlin/br/com/zup/zkotlin/coroutine/CoroutineListTest.kt)
    - suspend fun <T> List<Deferred<T>>.await()    
    
- Currying - [Examples](src/test/kotlin/br/com/zup/zkotlin/currying/CurryingTest.kt)
    - ::foobar(a,b).curried()
    - ::foobar(a,b,c).curried()
    - *max of 5 parameters foobar(a,b,c,d,e)*
    
- Int - [Examples](src/test/kotlin/br/com/zup/zkotlin/extensions/IntTest.kt)
    - fun isEven(): Boolean    
    - fun isOdd(): Boolean    

    

