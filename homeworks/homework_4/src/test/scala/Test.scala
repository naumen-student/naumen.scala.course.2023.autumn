import utest._

import scala.util.Random

object Test extends TestSuite {
    lazy val randomLength = Random
    def generateRandomList(maxListSize: Int) = {
        val listLength = Random.nextInt(maxListSize)
        List.fill(listLength)(Random.nextInt)
    }
    override def tests: Tests = Tests {
        'firstTask - (1 to 5).foreach { _ =>
            val testList = generateRandomList(50)
            val sumValue = testList(Random.nextInt(testList.size)) + testList(Random.nextInt(testList.size))
            assert(Exercises.findSumImperative(testList, sumValue) == Exercises.findSumFunctional(testList, sumValue))
        }

        'recursionTask - {
            'itWorks - {
                (1 to 5).foreach { _ =>
                    val testList = generateRandomList(50)
                    assert(Exercises.simpleRecursion(testList) == Exercises.tailRecRecursion(testList))
                }
            }
            'longList - {
                val testList = List.fill(10000)(Random.nextInt)
                Exercises.tailRecRecursion(testList)
            }
        }

        'binarySearch - {
            'simpleSearch - (1 to 5).foreach { _ =>
                val testList = generateRandomList(50).distinct.sorted
                val result = Random.nextInt(testList.size)
                assert(Exercises.functionalBinarySearch(testList, testList(result)).contains(result))
            }

            'empty - {
                assert(Exercises.functionalBinarySearch(Nil, 24).isEmpty)
            }

            'noElement - {
                val testList = List.fill(50)(Random.nextInt(50))
                assert(Exercises.functionalBinarySearch(testList, -1).isEmpty)
            }
        }

        'namesList - {
            'nonEmpty - (1 to 5).foreach { _ =>
                val namesCount = Random.nextInt(50)
                val names = Exercises.generateNames(namesCount)
                assert(
                    names.forall(_.matches("([A-Z]|[А-Я])([a-z]|[а-я])*")) &&
                        names.size == namesCount &&
                        names.distinct.size == namesCount
                )
            }

            'empty - {
                assert(Exercises.generateNames(0).isEmpty)
            }
        }
        'SideEffectExercise - {
            import Utils._
            import SideEffectExercise._
            import scala.collection.mutable

            def getPhoneServiceSafety(phones: mutable.ListBuffer[String]): PhoneServiceSafety = {
              val phoneBase = new PhoneBase(phones)
              val simplePhoneService = new SimplePhoneService(phoneBase)
              new PhoneServiceSafety(simplePhoneService)
            }

            'PhoneServiceSafety - {
                'findPhoneNumberSafe - {
                  'nonEmpty - {
                    val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer("71234567890"))
                    assert(phoneServiceSafety.findPhoneNumberSafe("71234567890").contains("71234567890"))
                  }

                  'empty - {
                    val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer.empty)
                    assert(phoneServiceSafety.findPhoneNumberSafe("99999999999").isEmpty)
                  }
                }
                'addPhoneToBaseSafe - {
                  'added - {
                    val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer.empty)
                    assert(phoneServiceSafety.addPhoneToBaseSafe("12345678901").isRight)
                    assert(phoneServiceSafety.findPhoneNumberSafe("12345678901").nonEmpty)
                  }
                  'nonAdded - {
                    val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer.empty)
                    assert(phoneServiceSafety.addPhoneToBaseSafe("InvalidPhone").isLeft)
                  }
                }
                'deletePhone - {
                  val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer("80987654321"))
                  phoneServiceSafety.deletePhone("80987654321")
                  assert(phoneServiceSafety.findPhoneNumberSafe("80987654321").isEmpty)
                }
            }
            'ChangePhoneServiceSafe - {
                'okIfPhoneDoseNotExist - {
                  val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer.empty)
                  val changePhoneServiceSafe = new ChangePhoneServiceSafe(phoneServiceSafety)
                  assert(changePhoneServiceSafe.changePhone("80987654321", "12345678901") == "ok")
                }
                'okIfPhoneAlredyExist - {
                  val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer("80987654321"))
                  val changePhoneServiceSafe = new ChangePhoneServiceSafe(phoneServiceSafety)
                  assert(changePhoneServiceSafe.changePhone("80987654321", "12345678901") == "ok")
                }
                'failedIfNewPhoneInvalid - {
                  val phoneServiceSafety = getPhoneServiceSafety(mutable.ListBuffer.empty)
                  val changePhoneServiceSafe = new ChangePhoneServiceSafe(phoneServiceSafety)
                  assert(changePhoneServiceSafe.changePhone("80987654321", "InvalidNumber") == "Failed to change the number: Invalid phone string")
                }
            }
        }
    }
}
