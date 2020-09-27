package juniar.nicolas.simplekoin

import android.app.Application
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MainApp : Application() {

    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()
        koinApplication = startKoin {
            modules(
                listOf(
                    networkModule
                )
            )
        }
    }
}