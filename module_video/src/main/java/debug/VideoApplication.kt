package debug

import android.app.Application
import com.cxz.kotlin.baselibs.config.AppConfig


/**
 * @author chenxz
 * @date 2018/12/22
 * @desc
 */
class VideoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
    }

}