package application;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Client;
import classes.Commande;
import classes.Facture;
import classes.Produit;
import controller.SampleController;
import controller.SupModifLivraisonController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
public class ConnectToDB {
	

//	etablir une connection a la db.
	public static Connection connectionDB() {
		
		
		String url = "jdbc:mysql://localhost:3306/gestioncommande";
		String user = "root";
		String password = "12345678";
		Connection connection = null;
		


		
//		String url = "jdbc:mysql://localhost:3306/gestioncommande";
//		String user = "root";
//		String password = "";
//		Connection connection = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		}
		
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		}
		
		System.out.println("Connextion reussite...");
		return connection;	
	
	}
	
//	prendre des donnes d'une collone d'un tableau preciser a partir d'une connection.
	
	public static ArrayList<String> getData(Connection connection,String table,String column) {
		
		try {
			Statement statement = connection.createStatement();
			
			String request = "SELECT * FROM " + table +" ;";
			
			
			ResultSet result = statement.executeQuery(request);
			
			ArrayList <String> results = new ArrayList<String>();
            
			while (result.next()) {
			    results.add(result.getString(column));
			}
			
			return results;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		}
		return null;
	}
	public static void insertClientData(Connection connection,Client client) {
	  try {
		  String query = "insert into client(nom,prenom,adresse,telephone) values "
		  		+ "(?,?,?,?);";
		  PreparedStatement prepare = connection.prepareStatement(query);
		 
		  prepare.setString(1, client.getNom());
		  prepare.setString(2, client.getPrenom());
		  prepare.setString(3, client.getAdresse());
		  prepare.setInt(4, client.getTelephone());
		  prepare.execute();
		  
		  
	  }
	  catch (SQLException e){
		      Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	  }
	  }
	
	public static void insertFactureData(Connection connection,Facture facture) {
		  try {
			  String query = "insert into Facture(datefacture,montant,numerocommande) values "
			  		+ "(?,?,?);";
			  PreparedStatement prepare = connection.prepareStatement(query);

			  prepare.setDate(1,Date.valueOf(facture.getDateFacture()));
			  prepare.setFloat(2,facture.getMontant());
			  prepare.setInt(3,(facture.getNumeroCommande()));
			  
			  prepare.execute();
			  
			  
		  }
		  catch (SQLException e){
			      Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		  }
}

	public static void insertLivraisonData(Connection connection,Livraison livraison) {
          try {
              String query = "insert into Livraison(datelivraison,numerocommande) values "
              		+ "(?,?);";
              PreparedStatement prepare = connection.prepareStatement(query);
              prepare.setDate(1,Date.valueOf(livraison.getDateLivraison()));
              prepare.setInt(2,livraison.getNumeroCommande());
              prepare.execute();
              
              
          }
          catch (SQLException e){
                  Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
          }
          }
public static void updatefacture(Connection connextion,Facture facture) {
	try {
		PreparedStatement prepare = connextion.prepareStatement(
				"Update facture set dateFacture = ? ,montant = ? ,numeroCommande = ? where numeroFacture = ? ");
		prepare.setInt(4, facture.getNumeroFacture());
		prepare.setDate(1, Date.valueOf(facture.getDateFacture()));
		prepare.setFloat(2, facture.getMontant());
		prepare.setInt(3, facture.getNumeroCommande());

		prepare.execute();
		SampleController.refreshfacture();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}

public static void updateLivraison(Connection connexion,Livraison livraison) {
	try {
		PreparedStatement prepare = connexion.prepareStatement(
				"Update livraison set datelivraison = ? ,numeroCommande = ? where numeroLivraison = ? ");

		prepare.setDate(1, Date.valueOf(livraison.getDateLivraison()));
		prepare.setInt(2, livraison.getNumeroCommande());
		prepare.setInt(3, livraison.getNumeroLivraison());

		prepare.execute();
		SupModifLivraisonController.refreshLivraison();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
	
public static ResultSet data(Connection connection,String table,String condition,String value){
	
	try {
		Statement statement;
		statement = connection.createStatement();

	
	
		return statement.executeQuery("select * from "+ table +" where " + condition + " = '"+ value + "' ;");
	}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		return null;
	}
}
public static void modifieuser(Connection connection,Client client) {
	

	try {

		 PreparedStatement prepare = connection.prepareStatement("Update client set nom = ? ,prenom = ? ,adresse = ? ,telephone = ? where numeroclient = ?");
		  prepare.setString(1, client.getNom());
		  prepare.setString(2, client.getPrenom());
		  prepare.setString(3, client.getAdresse());
		  prepare.setInt(4, client.getTelephone());
		  prepare.setInt(5, client.getId_client());
		  prepare.execute();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
public static ResultSet selecttous(Connection connection,String Table) {
	try {
		Statement statement;
		statement = connection.createStatement();
	
		return statement.executeQuery("select * from "+ Table );
	}
	catch (SQLException e) {
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		return null;
	}
	
}


	

	
	public static void delete(Connection connection, String table,String columnid,Integer ind) {
		try {
			Statement statement = connection.createStatement();
			
			String request = "DELETE FROM " + table +" WHERE " + columnid + " = "  +ind ;
			
			statement.executeUpdate(request);
			
			System.out.println("Suppresion reussite.");
		}
		catch(SQLException e) {
			    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		}
	}
//	public static void main(String[] args) {
//		Connection connexion = ConnectToDB.connectionDB();
//		getData(connexion,"facture","montant");
//	}

static void insertcommande(Connection connection,Commande commande) {
	try {
		PreparedStatement prepare = connection.prepareStatement("insert into commande(datecommande,numeroclient) values (?,?);");
		prepare.setDate(1,Date.valueOf(commande.getDatecomande()));
		prepare.setInt(2,commande.getNum_client());
		prepare.execute();
//		prepare = connection.prepareStatement("select numerocommande from commande DESC limit 1");
//		ResultSet numerocommand
		Statement statement;
		statement = connection.createStatement();
		ResultSet result = statement.executeQuery("select numerocommande from commande order by numerocommande DESC limit 1");
		result.next();
		int numcom = result.getInt("numerocommande");
		for(int i =0;i<commande.list_produit.size();i++) {
			prepare = connection.prepareStatement("insert into avoir(numerocommande,numeroproduit,Quantite_prod) values (?,?,?);");
			PreparedStatement prepare2 = connection.prepareStatement("Update produits set quantite = ? where numeroproduit;");
			prepare2.setInt(1,commande.list_produit.get(i).numProduit);
			prepare2.setInt(1,commande.list_produit.get(i).QuantiteProduit-Integer.parseInt((commande.list_produit.get(i).getQuantitetext().getText())));
			prepare2.execute();
			prepare.setInt(1,numcom);
			prepare.setInt(2,commande.list_produit.get(i).numProduit);
			prepare.setInt(3,Integer.parseInt((commande.list_produit.get(i).getQuantitetext().getText())));
			prepare.execute();
			
		}
		
	    
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
static ResultSet getproducts(Connection connection,int numcommmande){
	Statement statement;
	try {
		statement = connection.createStatement();
		return statement.executeQuery("select * from avoir,produits where avoir.numeroproduit = produits.numeroproduit and avoir.numerocommande = "+ Integer.toString(numcommmande)+ ";");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
		return null;
	}
}
static void supprimercommande(Connection connection , Commande commande) {
	
	try {
		PreparedStatement prepare = connection.prepareStatement("DELETE FROM avoir WHERE numerocommande = ?");
		prepare.setInt(1, commande.numerocommande);
		prepare.execute();
		prepare = connection.prepareStatement("delete from commande where numerocommande = ?");
		prepare.setInt(1, commande.numerocommande);
		prepare.execute();
	}  catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
	
}
static void  modifiercomande(Connection connection ,Commande commande) {
	try {
		PreparedStatement prepare = connection.prepareStatement("Update commande set numeroclient = ?,datecommande = ? where numerocommande = ?;");
		System.out.println(commande.num_client);
		prepare.setInt(1, commande.num_client);
		prepare.setDate(2, Date.valueOf(commande.getDatecomande()));
		prepare.setInt(3, commande.numerocommande);
		prepare.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
static void modifierproduit(Connection connection ,Produit produit,int numCommande) {
	try {
		PreparedStatement prepare = connection.prepareStatement("Update avoir set Quantite_prod = ? where numerocommande = ? and numeroproduit = ? ;");
		
		prepare.setInt(1, produit.getQuantitechoisie());
		prepare.setInt(2, numCommande);
		prepare.setInt(3, produit.getNumProduit());
		prepare.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
static void supprimerrproduit(Connection connection , int num_produit ,int num_commande) {
	try {
		PreparedStatement prepare = connection.prepareStatement("Delete from avoir  where numerocommande = ? and numeroproduit = ? ;");
		
		prepare.setInt(1,num_commande);
		prepare.setInt(2, num_produit);
		
		prepare.execute();
		System.out.println("llllllllll");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
static void modifierproduit(Connection connection,Produit produit) {
	try {

		 PreparedStatement prepare = connection.prepareStatement("Update produits set nomproduit = ? ,quantite = ? ,prix = ? where numeroproduit = ?");
		  prepare.setString(1, produit.getNomProduit());
		  prepare.setInt(2, produit.getQuantiteProduit());
		  prepare.setDouble(3, produit.getPrix());
		  prepare.setInt(4, produit.getNumProduit());
		  prepare.execute();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    });
	}
}
static void AJouterProduit(Connection connection ,Produit produit) {
	try {
		  String query = "insert into produits(numeroproduit,nomproduit,quantite,prix) values (?,?,?,?);";
		  PreparedStatement prepare = connection.prepareStatement(query);
		 
		  prepare.setInt(1, produit.getNumProduit());
		  prepare.setString(2, produit.getNomProduit());
		  prepare.setInt(3, produit.getQuantiteProduit());
		  prepare.setDouble(4, produit.getPrix());
		  prepare.execute();
		  
		  
	  }
	  catch (SQLException e){
		  e.getNextException();
}
}

}
