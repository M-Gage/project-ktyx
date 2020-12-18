import com.ywkj.ktyunxiao.common.utils.ListUtil;
import freemarker.template.utility.NumberUtil;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/7 11:58
 */
public class Ognl {

    /**
     * 可以用于判断是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return StringUtils.isEmpty(o);
        } else if (o instanceof Number) {
            return NumberUtil.isNaN((Number) o);
        } else {
            return false;
        }
    }

    /**
     * 可以用于判断是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 可以用于判断是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isTrue(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Boolean) {
            return (boolean) o;
        }else if (o instanceof String){
            return "true".equalsIgnoreCase((String) o);
        }else {
           return false;
        }
    }

    /**
     * 可以用于判断数组或者集合是否为空
     * @param o
     * @return
     */
    public static boolean isArray(Object o){
        if (o == null){
            return false;
        }
        if  (o instanceof List){
            return ListUtil.isNotEmpty((List) o);
        } else if (o.getClass().isArray()) {
            return Array.getLength(o) > 0;
        }
        return false;
    }

    /**
     * 可以用于判断对象不为零
     * @param o
     * @return
     */
    public static boolean isNotZero(Object o) {
        if (o instanceof Integer) {
            return (int) o != 0;
        }
        return false;
    }


}
