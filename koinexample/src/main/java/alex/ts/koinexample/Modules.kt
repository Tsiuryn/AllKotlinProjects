package alex.ts.koinexample

import org.koin.dsl.module

val appModule = module {
    //Defines a singleton of SchoolCourse
    single { SchoolCourse()}

    //Defines a factory (creates new instance every time)
    factory { Friend()}

    factory { Student(get(), get())}
}