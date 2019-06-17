import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.JdbcDaoSupport;
import com.itheima.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ziyin
 @create 2019-06-2019/6/17-11:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcDaoSupport.class)
public class TestSpring {

    @Autowired
    private IAccountDao accountDao ;

    @Test
    public void  test(){

        Account account = accountDao.findAccountById(1);
        System.out.println(account);

        account.setMoney(30000f);
        accountDao.updateAccount(account);
    }
}
