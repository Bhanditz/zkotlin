# zkotlin


Extensions for Kotlin Language

- Either<L, R>
    - Either.Left<L,R> Either.Right<L,R>
        - Either.right<Int, String>("right value)
        - Either.left<Int, String>(-1)
        - Either.rightOf("right value)
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
    - fun getOrElse(supplier: (L) -> Either<L, R>): Either<L, R>
    

