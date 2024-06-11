package version3.utils.data.extract;

public class UserDataExtractor {

    private static final String path_users = "res/version3/user/users.csv";

    public static String[] users = DataExtractor.listeData(path_users);

}
