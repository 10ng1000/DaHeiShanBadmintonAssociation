import com.dhsba.dao.AccountDao;

public class DaoTest {
    public static void main(String[] args){
        AccountDao accountDao = new AccountDao();
        //System.out.println(accountDao.createAccount("123","123"));
        System.out.println(accountDao.getAccount("123"));
    }
}
