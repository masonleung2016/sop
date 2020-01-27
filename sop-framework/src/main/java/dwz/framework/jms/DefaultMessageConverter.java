package dwz.framework.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:43
 * @Package: dwz.framework.jms
 */
public class DefaultMessageConverter {

    public Message toMessage(Object obj, Session session) throws JMSException {
        return null;
    }

    public Object fromMessage(Message msg) throws JMSException {
        return null;
    }
}
