# Scala basic II
# От type конструкторов до коллекций

Роман Деньгин

---
## IntBox

```scala
case class IntBox(
    value: Int
)
```

```
scala> IntBox(5)
res0: IntBox = IntBox(5)
```


---

## StringBox

```scala
case class StringBox(
    value: String
)
```
```
scala> StringBox("hello")
res0: StringBox = StringBox(hello)
```

---

## BooleanBox

```scala
case class BooleanBox(
    value: Boolean
)
```
```
scala> BooleanBox(true)
res1: BooleanBox = BooleanBox(true)
```

---
## Box[T]
```scala
case class Box[T](
    value: T
)
```
```
scala> Box[Int](5)
res2: Box[Int] = Box(5)

scala> Box[String]("hello")
res3: Box[String] = Box(hello)
```

---
```
scala> Box[String](5)

<console>:14: error: type mismatch;
 found   : Int(5)
 required: String
```

---

## Несколько параметров
```scala
case class Box2[A, B](
    value1: A,
    value2: B
)
```
```
scala> Box2(5, "hello")
res6: Box2[Int,String] = Box2(5,hello)
```

---

## Параметры функций
```scala
def toBox2[A, B](
  first: Box[A],
  second: Box[B]
): Box2[A, B] = Box2(first.value, second.value)
```
```
scala> toBox2(Box("hello"), Box(5))
res7: Box2[String,Int] = Box2(hello,5)
```

---

## Конкретный параметр
```scala
def sum(box: Box2[Int, Int]): Box[Int] =
  Box(box.value1 + box.value2)
```
```
scala> sum(Box2(1, 2))
res8: Box[Int] = Box(3)
```

---

## Игнорируем параметр
```scala
def getFirst[T](box: Box2[T, _]): T = box.value1
```
```
scala> getFirst(Box2("hello", 4))
res9: String = hello
```

---
## Upper Bounds

---
## <span class="naumen">Upper Bounds</span>
```scala
trait Animal {
  def name: String
}

case class Cat(
    override val name: String
) extends Animal

case class Dog(
    override val name: String
) extends Animal 
```

---
## <span class="naumen">Upper Bounds</span>
```scala
def shortestName(
    animals: List[Animal]
): Option[Animal] = animals.sortBy(_.name.length).headOption
```

---
## <span class="naumen">Upper Bounds</span>
```scala
def sayMeow(cat: Cat): String =
  cat.name + " says meow."

val cats: List[Cat] = List(
  Cat("Garfield"),
  Cat("Lucy"),
  Cat("Kuzya")
)
```

---
## <span class="naumen">Upper Bounds</span>
```scala
val cats: List[Cat] = ...

scala> val s = shortestName(cats)
s: Option[Animal] = Some(Cat(Lucy))

scala> s.map(cat => sayMeow(cat))
<console>:19: error: type mismatch;
 found   : Animal
 required: Cat 
```

---
## <span class="naumen">Upper Bounds</span>
```
scala> s.map { cat =>
  sayMeow(cat.asInstanceOf[Cat])
}

res7: Option[String] =
  Some(Lucy says meow.)
```
<span style="color:darkred">Грязный код.
Потенциальные ошибки. </span>

---
## <span class="naumen">Upper Bounds</span>
```
scala> val s =
  shortestName(List(Dog("Goofy")))
s: Option[Animal] = Some(Dog(Goofy))

scala> s.map { cat =>
  sayMeow(cat.asInstanceOf[Cat])
}

java.lang.ClassCastException:
  Dog cannot be cast to Cat
```

---
## <span class="naumen">Upper Bounds</span>
```scala
def shortestName[T](
    animals: List[T]
): Option[T] =
  animals.sortBy(_.name.length).headOption
```
```
<console>:11: error: value name is not a member of
type parameter T 
```

---
## <span class="naumen">Upper Bounds</span>
```scala
def shortestName[T <: Animal](
    animals: List[T]
): Option[T] =
  animals.sortBy(_.name.length).headOption
```

---
## <span class="naumen">Upper Bounds</span>
```
scala> val s = shortestName(cats)
s: Option[Cat] = Some(Cat(Lucy))

scala> s.map(sayMeow)
res11: Option[String] = Some(Lucy says meow.)
```

---
## <span class="naumen">Upper Bounds</span>
```
scala> val s =
  shortestName(List(Dog("Goofy")))
s: Option[Dog] = Some(Dog(Goofy))

scala> s.map(sayMeow)
<console>:20: error: type mismatch;
 found   : Cat => String
 required: Dog => ? 
```

---

## Lower Bounds

---
## <span class="naumen">Lower Bounds</span>
```scala
def addToCats(
    cats: List[Cat],
    other: List[Animal]
): List[Animal] =
  cats ++ other
```
```
scala> addToCats(
  List(Cat("Lucy")),
  List(Cat("Kuzya"))
)
res13: List[Animal] =
  List(Cat(Lucy), Cat(Kuzya))
```

---
## <span class="naumen">Lower Bounds</span>
```scala
def addToCats[T](
    cats: List[Cat],
    other: List[T]
): List[T] =
  cats ++ other
```
```
<console>:15: error: type mismatch;
 found   : List[Any]
 required: List[T]
```

---
## <span class="naumen">Lower Bounds</span>

```scala
def addToCats[T >: Cat](
    cats: List[Cat],
    other: List[T]
): List[T] =
  cats ++ other
```

```
scala> addToCats(
  List(Cat("Lucy")),
  List(Cat("Kuzya"))
)

res8: List[Cat] =
  List(Cat(Lucy), Cat(Kuzya))
```

---

## Несколько ограничений одновременно
```scala
def func[T >: Cat <: Animal with CanMove: Decoder: Encoder] =
  ...
```

---
## Kind

---
```scala

scala> def incorrect(box: Box) = box

<console>:13: error: class Box takes type parameters
```

<span class="naumen">Box не является типом!</span>

---

## Конкретный тип:
<span class="naumen">\*<span>
```scala
Int
String
Box[Int]
Box[Box[String]]
List[Double]
Box2[Double, String]
Map[String, Int]
```

---

## Конструктор типа с одним параметром:
<span class="naumen">\* -> *</span>
```scala
Box
List
Option
Box2[Int, *]
Map[*, Double]
```

---
## Конструктор типа с двумя параметрами:
<span class="naumen">\* -> * -> *</span>
```scala
Box2
Map
Either
```

---
<span class="naumen">(* -> *) -> *</span>

??? 

---
## Higher Kinds

(* -> *) -> * (<span class="naumen">конструктор типа</span>, который <span class="naumen">принимает</span> в качестве аргумента <span class="naumen">другой конструктор типа</span>)

Флаг компилятора: <span class="naumen">-language:higherKinds</span>
```scala
case class HigherKindedBox[T[_]](
    value: T[Int]
)
```

---
## Higher Kinds
```scala
scala> HigherKindedBox(Box(5))
res10: HigherKindedBox[Box] =
  HigherKindedBox(Box(5))

scala> HigherKindedBox(List(5, 6, 7))
res11: HigherKindedBox[List] =
  HigherKindedBox(List(5, 6, 7))
```

---
## Higher Kinds
```scala
scala> HigherKindedBox(5)

<console>:14: error: ...
argument expression's type is not compatible with formal parameter type;
```

---
## Higher Kinds
```
scala> HigherKindedBox(Box2("hello", 6))

??? 
```

---
## Higher Kinds
```
scala> HigherKindedBox(Box2("hello", 6))

<console>:16: error: ...
argument expression's type is not compatible with formal parameter type;

T      = Box2[String, *]
T[Int] = Box2[String, Int]
```

---

## Частичная унификация
Флаг компилятора:  <span class="naumen">-Ypartial-unification</span>

https://github.com/typelevel/kind-projector

```scala
box: HigherKindedBox[Box2[String, *]]=
  HigherKindedBox(Box2("hello", 1))
```
---







---
## Variance

---

```scala
def getName(box: Box[Animal]): String =
  box.value.name
```

```
scala> val box = Box(Cat("Kuzya"))
box: Box[Cat] = Box(Cat(Kuzya))

---
```
scala> getName(box)
<console>:19: error: type mismatch;
 found   : Box[Cat]
 required: Box[Animal]
```

Note\: Cat <span class="naumen"><:</span> Animal, <span class="naumen">but</span> class Box is <span class="naumen">invariant</span> in type T.


---

## Invariance
```scala
A =:= B => F[A] =:= F[B]

case class Box[T](value: T)

Box[Animal] =:= Box[Animal]

Box[Cat] =:= Box[Cat]

Box[Cat] <: Box[Animal] - неверно!
```

---
## Covariance
```scala
A <: B => F[A] <: F[B]

case class Box[+T](value: T)

Cat <: Animal => Box[Cat] <: Box[Animal]

scala> getName(box)
res2: String = Kuzya
```

---
## Почему всегда не использовать ковариантность?

---

## Contravariance
```scala
B <: A => F[A] <: F[B]

trait Printer[-T] {
  def print(value: T): String
}
```

---
## Contravariance
```scala
val catPrinter = new Printer[Cat] {
  override def print(cat: Cat): String =
    cat.name + " is a cat!"
}

val animalPrinter = new Printer[Animal] {
  override def print(animal: Animal): String =
    animal match {
      case Cat(name) => name + " is a cat!"
      case Dog(name) => name + " is a dog!"
    }
}
```

---
## Contravariance
<span class="naumen">Printer[Cat]</span> - может печатать <span class="naumen">только котов</span>

<span class="naumen">Printer[Animal]</span> - может печатать <span class="naumen">любых животных</span>
```
Cat <: Animal => Printer[Animal] <: Printer[Cat]
```

---
## Position variance
```scala
trait Printer[+T] {
  def print(value: T): String
}
```
Что произойдет?

---
## Position variance
```scala
trait Printer[+T] {
  def print(value: T): String
}

<console>:12: error: covariant type T occurs in contravariant position in type T of value value 
```

---
## Position variance
```scala
case class Box[-T](value: T)

<console>:11: error: contravariant type T occurs in covariant position in type => T of value value
```

---
## Position variance
```scala
trait Function[?A, ?B] {
  def apply(argument: A): B
}

val name = new Function[Animal, String] {
  override def apply(
      argument: Animal
  ): String =
    argument.name
}

scala> name(Cat("Kuzya"))
res15: String = Kuzya
```

---
## Position variance
```scala
trait Function[-A, +B] {
  def apply(argument: A): B
}
```

---
## Position variance
```scala
trait GetSet[?T] {
  def get: T

  def set(value: T): Unit
}
```

---
## Position variance
```scala
trait GetSet[T] {
  def get: T

  def set(value: T): Unit
} 
```

---
## Position variance
```scala
trait Function[-A, +B] {
  def apply(argument: A): B

  def andThen[C](
      f: Function[B, C]
  ): Function[A, C] = { argument: A =>
    f.apply(apply(argument))
  }
}

scala> val f = name.andThen(_.length)
scala> f(Cat("Garfield"))
res0: Int = 8
```

---

## Примеры type конструкторов стандартной библиотеки Scala

Option[T] - <span class="naumen">0</span> или <span class="naumen">1</span> элемент типа Т

List[T] - <span class="naumen">список</span> значений типа T

и другие коллекции

---

## Коллекции

- Option;
- множества (sets); 
- отображения (maps);
- последовательности (sequences). 

---

## Мутабельность

По умолчанию используются <span class="naumen">иммутабельные</span> (неизменяемые) коллекции.

```scala
val l = List(1, 2, 3)
//l += 4  // value += is not a member of List[Int]
//l(0) = 4000  // value update is not a member of List[Int]

val l2 = collection.mutable.MutableList(1, 2, 3) 
// MutableList(1000, 2, 3, 4)
l2 += 4
l2(0) = 1000

val a = Array(1, 2, 3) // Array(1000, 2, 3)
//a += 4  // value += is not a member of Array[Int]
a(0) = 1000
```

---

### scala.collection.immutable

![](images/immutable.svg)

---

### scala.collection.mutable

![](images/mutable.svg)

---

## Option

- Располагается в пакете `scala`, а не вместе с коллекциями: <span class="naumen">`scala.Option`</span>;
- Может содержать ноль (<span class="naumen">`None`</span>) или один (<span class="naumen">`Some`</span>) элемент.

```scala
val some: Option[Int] = Some(1)
// some: Option[Int] = Some(1)

val none: Option[Int] = None
// none: Option[Int] = None
```

---
### Array

![](images/one-dimension-array-in-java.png)

```scala
val arr1 = new Array[Int](5) 
//arr1: Array[Int] = Array(0, 0, 0, 0, 0)

val arr2 = Array(5, 4, 3, 2, 1) 
//arr2: Array[Int] = Array(5, 4, 3, 2, 1)

val arr3 = Array(5) 
//arr3: Array[Int] = Array(5)
```

---

## Списки: List
![](images/linked-list.png)

---

### Списки: List

- <span class="naumen">Однородны;</span>
  - `List[String]` не может содержать `Int`;
- <span class="naumen">ковариантны;</span>
  - если `T` подтип `S`, то и `List[T]` подтип `List[S]`.

```scala
sealed trait Animal
case class Cat() extends Animal
case class Dog() extends Animal

val l: List[Animal] = List(Cat(), Cat(), Cat())
```

---

### List: Создание 1

```scala
val list0 = List() 
// list0: List[Nothing] = List()

val list1 = List(1, 2, 3) 
// list1: List[Int] = List(1, 2, 3)

val list2 = List.range(1,5) 
// list2: List[Int] = List(1, 2, 3, 4)
val list2a = List.range(1, 5, 2) 
// list2a: List[Int] = List(1, 3)

val list3 = List.fill(5)("t") 
// list3: List[String] = List("t", "t", "t", "t", "t")

val list4 = List.tabulate(5)(n => n/2.0) 
// list4: List[Double] = List(0.0, 0.5, 1.0, 1.5, 2.0)
```

---

### List: Создание 2

<span class="naumen">`Nil` - пустой список.</span>

```scala
val empty = Nil 
// empty: Nil.type = List()

val list = 3 :: 2 :: 1 :: Nil 
// list: List[Int] = List(3, 2, 1)

val d = Nil 
// d: Nil.type = List()
val c = 3 :: d 
// c: List[Int] = List(3)
val b = 2 :: c 
// b: List[Int] = List(2, 3)
val a = 1 :: b 
// a: List[Int] = List(1, 2, 3)
```

---

### List: Шаблоны

```scala
val List(a, b, c) = List(1, 2, 3)
// a: Int = 1
// b: Int = 2
// c: Int = 3

val x :: y :: z = List(1, 2, 3)
// x: Int = 1
// y: Int = 2
// z: List[Int] = List(3)

val m :: n :: k = List(1, 2, 3, 4, 5)
// m: Int = 1
// n: Int = 2
// k: List[Int] = List(3, 4, 5)


//val m :: n :: Nil = List(1, 2, 4)  // scala.MatchError
```

---

### Пример

```scala
List(1,2,3) match {
  case x => "one"
  case x :: y => "two"
  case x :: y :: z => "three"
}
// res0: String = one
```

---

```scala

List(1,2,3,4,5) match {
  case x :: y :: z => "three"
  case x :: y => "two"
  case x => "one"
}
// res1: String = "three"



List(1,2,3) match {
  case x :: Nil => "one"
  case x :: y :: Nil => "two"
  case x :: y :: z :: Nil => "three"
}
// : String = "three"
```

---

## Методы первого порядка

---

### Length, indices

```scala
val len1 = List(1, 2, 3).length
//len1: Int = 3

val len2 = List().length 
//len2: Int = 0

val len3 = Nil.length 
//len3: Int = 0

val idx1 = List(1, 2, 3).indices 
//idx1: Range = Range(0, 1, 2)

val idx2 = Nil.indices 
//idx2: Range = Range()


```

---

### Head, tail, isEmpty 

```scala
val head = List(1, 2, 3).head 
//head: Int = 1

List(1, 2, 3).headOption
// res5: Option[Int] = Some(1)

val tail = List(1, 2, 3, 4, 5).tail 
//tail: List[Int] = List(2, 3, 4, 5)

val isEmpty = List(1, 2, 3, 4, 5).isEmpty 
//isEmpty: Boolean = false

val isEmptyNil = Nil.isEmpty 
//isEmptyNil: Boolean = true
```

---

### Contains, Distinct

```scala
List(1, 2, 3).contains(3) 
// Boolean = true

List(1, 2, 3).contains(0) 
// Boolean = false

List(1,2,2,3,3,3).distinct
// res2: List[Int] = List(1, 2, 3)

case class Cat(name: String)
List(Cat("Sam"), Cat("Nyan"), Cat("Nyan")).distinct
// List(Cat("Sam"), Cat("Nyan"))

class SimpleCat(name: String)
(new SimpleCat("Nyan") :: new SimpleCat("Nyan") :: Nil)
  .distinct.length
// 2

```

---

### Last, init

```scala
val abcde = List('a', 'b', 'c', 'd', 'e') 
//abcde: List[Char] = List('a', 'b', 'c', 'd', 'e')

val last1 = abcde.last 
//last1: Char = 'e'

val init1 = abcde.init 
//init1: List[Char] = List('a', 'b', 'c', 'd')

List().init 
//java.lang.UnsupportedOperationException: init of empty list
//  scala.collection.immutable.Nil$.init(List.scala:596)
//  ...

List().last 
//java.util.NoSuchElementException: last of empty list
//  scala.collection.immutable.Nil$.last(List.scala:595)
//  ...
```

---

### Reverse

```scala
val edcba = List("e", "d", "c", "b", "a") 
//edcba: List[String] = List("e", "d", "c", "b", "a")

val reverse1 = edcba.reverse 
//reverse1: List[String] = List("a", "b", "c", "d", "e")
```

---

### Drop, take, splitat

```scala

val abcde = List('a', 'b', 'c', 'd', 'e') 

val teke1 = abcde.take(2) 
//teke1: List[Char] = List('a', 'b')

val drop1 = abcde.drop(2) 
//drop1: List[Char] = List('c', 'd', 'e')

val splitat1 = abcde.splitAt(2) 
//(List('a', 'b'), List('c', 'd', 'e'))

```

---

### Flatten

```scala

List(List(1, 2), List(3), List(), List(4,5)).flatten 
//List[Int] = List(1, 2, 3, 4, 5)

val flatten2 = List(1, 2, 3).flatten 
//No implicit view available from Int => IterableOnce[B]
//val flatten2 = List(1, 2, 3).flatten                                                                                                                  

List(  List(List(), List(1))  ,  List(List(2)) ).flatten 
//List[List[Int]] = List(List(), List(1), List(2))

List(Some(1), Some(2), None).flatten
// : List[Int] = List(1, 2)

```

---

### Zip и Unzip

```scala

val abcde = List('a', 'b', 'c', 'd', 'e') 

val zip1 = abcde.indices.zip(abcde) 
//  Vector((0, 'a'), (1, 'b'), (2, 'c'), (3, 'd'), (4, 'e'))

val zip2 = abcde.zip(List(1, 2, 3)) 
// List(('a', 1), ('b', 2), ('c', 3))

val zipIdx = abcde.zipWithIndex 
// List(('a', 0), ('b', 1), ('c', 2), ('d', 3), ('e', 4))

val unzip = zip2.unzip 
// (List('a', 'b', 'c'), List(1, 2, 3))

```

---

### Добавление элемента

<span class="naumen">List</span> - <span class="naumen">плохой</span> выбор для частой вставки <span class="naumen">в конец.

```scala
3 :: List(1,2)
// res3: List[Int] = List(3, 1, 2)

//List(1, 2) :: 3 // value :: is not a member of Int

List(1, 2) :+ 3
// res4: List[Int] = List(1, 2, 3)
3 +: List(1, 2)
// res5: List[Int] = List(3, 1, 2)

```

---

### Объединение списков

```scala
// Операция только для листов

val `2list1` = List(1, 2) ::: List(3, 4, 5) 
//`2list1`: List[Int] = List(1, 2, 3, 4, 5)

val `2list2`= List() ::: List(1, 2, 3) 
//`2list2`: List[Int] = List(1, 2, 3)

val `2list3` = Nil  ::: List(1, 2, 3) 
//`2list3`: List[Int] = List(1, 2, 3)

// Операция для любых коллекций

List(1, 2, 3) ++ List(4, 5)
// res5: List[Int] = List(1, 2, 3, 4, 5)
List(1,2) ++ Some(3)
// res8: List[Int] = List(1, 2, 3)
List(1,2) ++ None
// res9: List[Int] = List(1, 2)
```

---

### toString, mkString

```scala

val abcde = List('a', 'b', 'c', 'd', 'e') 
val abcStr = abcde.toString 
//abcStr: String = "List(a, b, c, d, e)"

val list = List(1, 2, 3, 4, 5)
val lstStr = list.toString 
//lstStr: String = "List(1, 2, 3, 4, 5)"

val str1 = abcde.mkString ("[", ",", "]") 
//str1: String = "[a,b,c,d,e]"

val str3 = abcde.mkString (",") 
//str3: String = "a,b,c,d,e"

//val str4 = abcde.mkString (",", "]") 
```

---

## Методы высшего порядка

---

### Map, foreach

```scala
List(1,2,3).map(_.toString())
// res0: List[String] = List("1", "2", "3")

List(1,2,3).foreach(_.toString()) // ()
List(1,2,3).foreach(println)
// 1
// 2
// 3
```

---

### FlatMap

```scala
val listOpt = List(1,2,3).map(Some(_))
// listOpt: List[Some[Int]] = List(Some(1), Some(2), Some(3))
listOpt.flatten
// res3: List[Int] = List(1, 2, 3)

List(1,2,3).flatMap(Some(_))
// res4: List[Int] = List(1, 2, 3)
```

---

### map, flatmap

```scala
val li = 3 :: 2 :: 1 :: Nil
val filterOpt: Int => Option[Int] = x => if (x % 2 == 0) Some(x) else None

for {
    item <- li
    itemPlus = item + 1
    itemAfterOpt <- filterOpt(itemPlus)
} yield itemAfterOpt
// List(4, 2)
```

```scala
li.map { item => val itemPlus = item + 1; (item, itemPlus) }
    .flatMap { case (item, itemPlus) =>
        filterOpt(itemPlus)
            .map(itemAfterOpt => itemAfterOpt)
    }
```

---

### Filter, Partition

```scala
val filter1 = List(1, 2, 3, 4, 5).filter(_ % 2 == 0) 
//filter1: List[Int] = List(2, 4)

val filter2 = List(1, 2, 3, 4, 5).filter(_ < 0)  
/filter2: List[Int] = List()

val partition1 = List(1, 2, 3, 4, 5).partition(_ % 2 == 0) 
//(List[Int], List[Int]) = (List(2, 4), List(1, 3, 5))

val partition2 = List(1, 2, 3, 4, 5).partition(_ < 0) 
//(List[Int], List[Int]) = (List(), List(1, 2, 3, 4, 5))
```

---

### Find

```scala
val find1 = List(1, 2, 3, 4, 5).find(_ % 2 == 0) 
//find1: Option[Int] = Some(2)

val find2 = List(1, 2, 3, 4, 5).find(_ <= 0) 
//find2: Option[Int] = None
```

---

### GroupBy

```scala
List(Cat("Sam"), Cat("Nyan"), Cat("Nyan")).groupBy(_.name)
//HashMap(
//     "Sam" -> List(Cat("Sam")), 
//     "Nyan" -> List(Cat("Nyan"), Cat("Nyan")
//)
)
```

---

### DistinctBy

```scala

List(Cat("Sam"), Cat("Nyan"), Cat("Nyan")).groupBy(_.name)
//HashMap(
//     "Sam" -> List(Cat("Sam")), 
//     "Nyan" -> List(Cat("Nyan"), Cat("Nyan")
//)

List(Cat("Sam"), Cat("Nyan"), Cat("Nyan"))
  .groupBy(_.name)
  .flatMap(_._2.headOption)
// List(Cat(name = "Sam"), Cat(name = "Nyan"))

List(Cat("Sam"), Cat("Nyan"), Cat("Nyan")).distinctBy(_.name)
// List(Cat(name = "Sam"), Cat(name = "Nyan"))

```


---


### TakeWhile, DropWhile

```scala
val takeWhile1 = List(1, 2, 3, -4, 5).takeWhile(_ > 0)  
//takeWhile1: List[Int] = List(1, 2, 3)

val dropWhile1 = List("banana", "pear", "apple", "orange")
        .dropWhile(_.startsWith("b")) 
//dropWhile2: List[String] = List("pear", "apple", "orange")
```

---

### Span

```scala
val lst = List(1, 2, 3, -4, 5)
val span1 = lst.span(_ > 0) 
//span1: (List[Int], List[Int]) = (List(1, 2, 3), List(-4, 5))

val span2 = (lst.takeWhile(_ > 0), lst.dropWhile(_ > 0)  ) 
//span2: (List[Int], List[Int]) = (List(1, 2, 3), List(-4, 5))
```

---

### ForAll, Exist

```scala
val forall1 = List(1, 2, 3).forall( _ > 0) 
//forall1: Boolean = true

val forall2 = List(1, 2,-3).forall( _ > 0) 
//forall2: Boolean = false

val exists1 = List(1, 2,-3).exists( _ < 0) 
//exists1: Boolean = true

val exists2 = List(1, 2, 3).exists( _ < 0) 
//exists2: Boolean = false

```

---

### FoldLeft, FoldRight

```scala
val num = List(1, 2, 3)
val foldLeft1 = num.foldLeft(0)(_ + _) 
//foldLeft1: Int = 6

val foldLeft2 = num.foldLeft(-6)(_ + _) 
//foldLeft2: Int = 0

val foldRight1 = num.foldRight(0)(_ + _) 
//foldRight1: Int = 6

val foldRight2 = num.foldRight(-6)(_ + _) 
//foldRight2: Int = 0
```

---

### FoldLeft, FoldRight

![](images/fold.png)

---

### Нагляднее

```scala
List(1,2,3).foldLeft("0")((res, num) => res + num)
res1: String = 0123

List(1,2,3).foldRight("0")((num, res) => res + num)
res0: String = 0321
```

---

### Fold

```scala
val lst = List("a", "b", "c", "d")
// lst: List[String] = List("a", "b", "c", "d")

val foldLeft1 = lst.foldLeft("z")(_ + _)  
// foldLeft1: String = "zabcd"
val fold = lst.fold("z")(_ + _)
// fold: String = "zabcd"

List(1,2,3).fold("")(_ + _.toString())
// res5: Any = "123"

val opt: Option[Int] = None
// opt: Option[Int] = None
opt.fold(0)(_ + 42)
// res6: Int = 0
opt.map(_ + 42).getOrElse(0)
```

---

### ReduceLeft, reduceRight

```scala

val num = List(1, 2, 3)
val reduceLeft1 = lst.reduceLeft(_ + _) 
//reduceLeft1: Int = 6

val reduceLeft2 = num.reduceLeft(_ min _) 
//reduceLeft2: Int = 1

val reduceRight1 = num.reduceRight(_ + _) 
//reduceRight1: Int = 6

val reduceRight2 = num.reduceRight(_ max _) 
//reduceRight2: Int = 3
```

---

### Reduce vs fold

```scala

val emptyLst = List.empty[String]

val foldLeftEmpty = emptyLst.foldLeft("i")(_ + _) 
//foldLeftEmpty: String = "i"

List.empty[Int].reduceLeft(_ + _)
//UnsupportedOperationException: empty.reduceLeft

List.empty[Int].reduceLeftOption(_ + _)
// : Option[Int] = None
```

---

### Sorted

```scala
val a = List(10, 5, 8, 1, 7).sorted
//sort4: List[Int] = List(1, 5, 7, 8, 10)

case class Cat (name: String, age: Int)

val s = List(Cat("Мурзик", 2), Cat("Murka", 1)).sorted
//  error: No implicit Ordering defined for Cat.
```

---

### SortWith

```scala
List(4, 1, 5, 2, 3).sortWith(_ > _)
// res9: List[Int] = List(5, 4, 3, 2, 1)

case class Cat(name: String, age: Int)

val s = List(Cat("Мурзик", 2), Cat("Murka", 1))
// s: List[Cat] = List(Cat("Мурзик", 2), Cat("Murka", 1))

s.sortWith(_.name < _.name)
// res10: List[Cat] = List(Cat("Murka", 1), Cat("Мурзик", 2))
```

---

### Представления

Представление - это <span class="naumen">базовая коллекция</span>, для которой все трансформеры выполняются <span class="naumen">ленивым</span> образом.

```scala
List(1, 2, 3)
  .map(_ * 2) // List(2, 4, 6)
  .map(_ + 3) // List(5, 7, 9)

List(1, 2, 3).view
  .map(_ * 2)
  .map(_ + 3)
  .toList // List(5, 7, 9)
```

---


## Спасибо за внимание

- https://docs.scala-lang.org/overviews/collections-2.13/introduction.html