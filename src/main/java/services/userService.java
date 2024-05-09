package services;

import models.User;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class userService implements IService<User> {

    private Connection connection;

    private static User user;

    public void setUser(User user){
        this.user = user ;
    }

    static public User getUser(){
        return user;
    }

    public userService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "insert into user (`username`, `cin`, `date_birth`, `email_user`, `country`, `password`, `role`) values (?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setInt(2, user.getCin());
        preparedStatement.setDate(3, java.sql.Date.valueOf(user.getDate_birth()));
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getCountry());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setString(7, user.getRole());
        preparedStatement.executeUpdate();

    }
    @Override
    public List<User> recuperer() throws SQLException {
        String sql = "SELECT * FROM `user`";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setUsername(rs.getString("username"));
            p.setEmail(rs.getString("email_user"));
            p.setCountry(rs.getString("Country"));
            p.setCin(rs.getInt("Cin"));
            p.setRole(rs.getString("Role"));
            p.setDate_birth(rs.getDate("date_birth").toLocalDate());





            users.add(p);

        }
        return users;
    }

    @Override
    public void modifier(User user) throws SQLException {

    }
/*
    // In userService.java

    public String generateVerificationCode() {
        // Generate a random 6-digit verification code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }*/

    public void ResetPassword(String email, String password) {
        try {

            String req = "UPDATE user SET password = ? WHERE email_user = ?";
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, password);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("Password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

    public int ChercherMail(String email) {

        try {
            String req = "SELECT * from user WHERE email_user ='" + email + "'  ";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
               return 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
/*
    @Override
    public void modifier(User user) throws SQLException {

        String sql = "UPDATE user SET FirstName = ?,LastName = ?,Email = ?,Address = ?,Role = ?,Number = ?,Rating = ?,Password = ? WHERE idUser = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getRole());
            statement.setInt(6, user.getNumber());
            statement.setDouble(7, user.getRating());
            statement.setString(8, user.getPassword());
            statement.setInt(9, user.getId());
            statement.executeUpdate();


        }*/

    public void modifierByEmail(User user,String email) throws SQLException {

        String sql = "UPDATE user SET username = ?,`cin`= ?,`date_birth`= ?,`email_user`= ?,`country`= ?,`password`= ?,`role`= ? WHERE email_user = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setInt(2, user.getCin());
        preparedStatement.setDate(3, java.sql.Date.valueOf(user.getDate_birth()));
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getCountry());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setString(7, user.getRole());
        preparedStatement.setString(8,email);

        preparedStatement.executeUpdate();


    }



    @Override
    public void supprimer(int id) throws SQLException {
        String requete = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

/*
    public void supprimerEmail(String email) throws SQLException {
        String requete = "DELETE FROM user WHERE Email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
*/


    public boolean checkEmailExists(String email) {

        boolean result = false;

        try {
            String req = "SELECT * FROM user WHERE email_user = ?";
            PreparedStatement st = connection.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return result;
    }
/*
    public User readById(int id) {
        User u = null;
        try  {
            String req ="SELECT * from user WHERE idUser = '" + id +"'";
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                u= new User(rs.getInt("Id"),rs.getString("First Name"), rs.getString("LastName"),rs.getString("Email"), rs.getString("Address"), rs.getDouble("Rating"),rs.getString("Role"), rs.getInt("Number"),"");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return u;
    }


    public  int getUserId(String emailU){
        String req ="SELECT id FROM users WHERE email = emailU ";

        int x=0;
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
             x= rs.getInt("Id");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return x;

    }
*/


    public List<User> searchUsersByEmailStartingWithLetter(String searchAttribute,String startingLetter) throws SQLException{
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM user WHERE " + searchAttribute + " LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, startingLetter + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt("id"));
                p.setUsername(rs.getString("username"));
                p.setEmail(rs.getString("email_user"));
                p.setCountry(rs.getString("Country"));
                p.setCin(rs.getInt("Cin"));
                p.setRole(rs.getString("Role"));
                p.setDate_birth(rs.getDate("date_birth").toLocalDate());





                users.add(p);

            }

        } catch (SQLException ex) {
            System.out.println("Error while searching for users by email: " + ex.getMessage());
        }

        return users;
    }


    public String authentification(String email, String password) {
        String Role = null;
        try{
        String req = "SELECT * from user WHERE email_user = ? AND Password = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
             if(rs.next()) {
                 return rs.getString("role");
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }


        return Role;
    }


    public User getUserFromDatabase(String email, String password) throws SQLException {
        String role = authentification(email, password);

        // If authentication successful, retrieve user details
        if (role != null) {
            try {
                String req = "SELECT * from user WHERE email_user = ? and password= ?";
                PreparedStatement ps = connection.prepareStatement(req);
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    int cin = rs.getInt("cin");
                    Date dateOfBirth = rs.getDate("date_birth");
                    String country = rs.getString("country");

                    User user = new User(id, username, email, country, cin, role, dateOfBirth.toLocalDate(), password);
                    return user;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        // If authentication failed or user not found, return null
        return null;
    }


    public String getHashedPasswordByEmail(String email) {
        String hashedPassword = null;
        String req = "SELECT Password FROM user WHERE Email = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hashedPassword = rs.getString("Password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hashedPassword;
    }
    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Construire l'objet User à partir des données récupérées
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setCin(rs.getInt("cin"));
                user.setDate_birth(rs.getDate("date_birth").toLocalDate());
                user.setEmail(rs.getString("email_user"));
                user.setCountry(rs.getString("country"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }


}
