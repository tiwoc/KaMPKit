package co.touchlab.kampkit

import co.touchlab.kampkit.db.KaMPKitDb
import co.touchlab.kermit.Logger
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

fun initKoinIos(
    userDefaults: NSUserDefaults,
    appInfo: AppInfo,
    doOnStartup: () -> Unit
): KoinApplication = initKoin(
    module {
        single<Settings> { AppleSettings(userDefaults) }
        single { appInfo }
        single { doOnStartup }
    }
)

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(KaMPKitDb.Schema, "KampkitDb") }
}

// Access from Swift to globally create a logger
@Suppress("unused")
fun loggerWithTag(tag: String) =
    GlobalContext.get().get<Logger>(qualifier = null) { parametersOf(tag) }
