package xprinter.xpos.market.myapplication.Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Administrator on 2017-08-18.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextType {
    String value()default "application";
}
