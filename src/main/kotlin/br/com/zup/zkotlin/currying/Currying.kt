package br.com.zup.zkotlin.currying

fun <A, B, R> ((A, B) -> R).curried(): (A) -> (B) -> R =
        { p1: A ->
            { p2: B -> this(p1, p2) }
        }


fun <A, B, C, R> ((A, B, C) -> R).curried(): (A) -> (B) -> (C) -> R =
        { p1: A ->
            { p2: B ->
                { p3: C -> this(p1, p2, p3) }
            }
        }


fun <A, B, C, D, R> ((A, B, C, D) -> R).curried(): (A) -> (B) -> (C) -> (D) -> R =
        { p1: A ->
            { p2: B ->
                { p3: C ->
                    { p4: D -> this(p1, p2, p3, p4) }
                }
            }
        }


fun <A, B, C, D, E, R> ((A, B, C, D, E) -> R).curried(): (A) -> (B) -> (C) -> (D) -> (E) -> R =
        { p1: A ->
            { p2: B ->
                { p3: C ->
                    { p4: D ->
                        { p5: E -> this(p1, p2, p3, p4, p5) }
                    }
                }
            }
        }
