// Annotations associate meta-information with definitions. 
// For example, the annotation @deprecated before a method causes the compiler to print a warning if the method is used

"""
Аннотации в Scala — это механизм, который позволяет добавлять метаданные к классам, методам, переменным, параметрам или другим элементам кода. 
Они не изменяют поведение программы напрямую, но могут использоваться для:
    генерации кода,
    управления компиляцией,
    работы с библиотеками,
    добавления информации, которая может быть обработана во время выполнения (через рефлексию) или компиляции.
"""


class MyClass {
  @deprecated("Этот метод устарел, используйте новый метод", "v1.0")
  def oldMethod(): Unit = {
    println("Старый метод")
  }

  def newMethod(): Unit = {
    println("Новый метод")
  }
}

val obj = new MyClass()
obj.oldMethod() // Компилятор выдаст предупреждение




"""
Аннотации в Scala — это мощный инструмент для:
    Добавления метаданных.
    Управления процессом компиляции.
    Генерации кода через макросы.
    Улучшения читаемости и функциональности кода.
Если вы работаете с большими системами или библиотеками (например, Play Framework, Akka), аннотации становятся особенно важными для интеграции и генерации необходимого функционала.
"""


