package com.messon.faculty.app.di


import com.messon.faculty.app.data.remote.network.CsvUpload
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {
    single { CsvUpload(get()) }
    single { CsvRepository(get()) }

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
        AddStudentManuallyVM()
    }

    viewModel {
        StudentDetailScreenVM()
    }

}





expect val platformModule: Module