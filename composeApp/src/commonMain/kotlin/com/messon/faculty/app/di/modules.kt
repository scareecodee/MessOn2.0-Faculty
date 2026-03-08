package com.messon.faculty.app.di


import com.messon.faculty.app.data.remote.network.Auth
import com.messon.faculty.app.data.remote.network.CollegeApi
import com.messon.faculty.app.data.remote.network.CsvUpload
import com.messon.faculty.app.data.remote.network.ManagerApi
import com.messon.faculty.app.data.remote.network.MessApi
import com.messon.faculty.app.data.remote.network.StudentApi
import com.messon.faculty.app.domain.repository.AuthRepository
import com.messon.faculty.app.domain.repository.CollegeRepository
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.domain.repository.ManagerRepository
import com.messon.faculty.app.domain.repository.MessRepository
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.viewmodel.AddManagerVM
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.AuthViewModel
import com.messon.faculty.app.presentation.viewmodel.CollegeScreenVM
import com.messon.faculty.app.presentation.viewmodel.CreateMessVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.ManagerListVM
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
    single { ManagerApi(get()) }



    single{ StudentRepository(get()) }
    single{ CollegeRepository(get()) }
    single{ MessRepository(get()) }
    single { CsvRepository(get()) }
    single{ ManagerRepository(get()) }




    single { Auth(get()) }
    single { AuthRepository(get()) }

    viewModel {
        CsvUploadViewModel(
            get(),
            secureStorage = get()
        )
    }

    viewModel {
        FilePickerViewModel(

        )
    }

    viewModel {
        AddStudentManuallyVM(
            get(),
            secureStorage = get()
        )
    }

    viewModel {
        StudentDetailScreenVM(
            studentRepository =get()
        )
    }

    viewModel {
        StudentScreenVM(
            get(),
            secureStorage = get()
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

    viewModel {
        AddManagerVM(
            managerRepository = get(),
            secureStorage = get()
        )
    }

    viewModel {
        ManagerListVM(
            managerRepository = get(),
            secureStorage = get()
        )
    }

}


expect val platformModule: Module