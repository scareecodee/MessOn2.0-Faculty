package com.messon.faculty.app.di


import com.messon.faculty.app.data.remote.network.CsvUpload
import com.messon.faculty.app.data.remote.network.Student
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {
    single { CsvUpload(get()) }
    single { CsvRepository(get()) }
    single { Student(get()) }
    single{ StudentRepository(get()) }

    viewModel {
        CsvUploadViewModel(
            get ()
        )
    }

    viewModel {
        FilePickerViewModel(

        )
    }

    viewModel {
        AddStudentManuallyVM(get())
    }

    viewModel {
        StudentDetailScreenVM(
            studentRepository =get()
        )
    }

    viewModel {
        StudentScreenVM(
            get()
        )
    }

}





expect val platformModule: Module