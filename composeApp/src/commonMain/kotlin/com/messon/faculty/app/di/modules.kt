package com.messon.faculty.app.di


import com.messon.faculty.app.data.remote.network.Auth
import com.messon.faculty.app.data.remote.network.CollegeApi
import com.messon.faculty.app.data.remote.network.CsvUpload
import com.messon.faculty.app.data.remote.network.MessApi
import com.messon.faculty.app.data.remote.network.StudentApi
import com.messon.faculty.app.domain.repository.AuthRepository
import com.messon.faculty.app.domain.repository.CollegeRepository
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.domain.repository.MessRepository
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.AuthViewModel
import com.messon.faculty.app.presentation.viewmodel.CollegeScreenVM
import com.messon.faculty.app.presentation.viewmodel.CreateMessVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {
    single { CsvUpload(get()) }

    single { StudentApi(get()) }
    single { MessApi(get()) }
    single { CollegeApi(get()) }

    single{ StudentRepository(get()) }
    single{ CollegeRepository(get()) }
    single{ MessRepository(get()) }
    single { CsvRepository(get()) }



    single { Auth(get()) }
    single { AuthRepository(get()) }

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

    viewModel {
        EditStudentVM(get())
    }

    viewModel {
        AuthViewModel(
            secureStorage = get(),
            authRepository = get(),
            dispatcher = Dispatchers.IO,
            collegeRepository =get()
        )
    }

    viewModel {
        MessListVM(
            get(),get()
        )
    }

    viewModel {
        CreateMessVM(
            messRepository = get(),
            secureStorage = get()
        )
    }

    viewModel {
        CollegeScreenVM(
            collegeRepository = get()
        )
    }

}


expect val platformModule: Module