package com.group4.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.java.mongo.Futbolista;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
//import com.sun.java.util.jar.pack.Package.File;

public class StringUtil {
	
	public static DBObject method(Map c){
		ArrayList<Futbolista> futbolistas = new ArrayList<Futbolista>();

		futbolistas.add(new Futbolista("Iker", "Casillas", 33, new ArrayList<String>(Arrays.asList("Portero")), true));
		futbolistas.add(new Futbolista("Carles", "Puyol", 36, new ArrayList<String>(Arrays.asList("Central", "Lateral")), true));
		futbolistas.add(new Futbolista("Sergio", "Ramos", 28, new ArrayList<String>(Arrays.asList("Lateral", "Central")), true));
		futbolistas.add(new Futbolista("Andr�s", "Iniesta", 30, new ArrayList<String>(Arrays.asList("Centrocampista", "Delantero")), true));
		futbolistas.add(new Futbolista("Fernando", "Torres", 30, new ArrayList<String>(Arrays.asList("Delantero")), true));
		futbolistas.add(new Futbolista("Leo", " Baptistao", 22, new ArrayList<String>(Arrays.asList("Delantero")), false));
		Futbolista b = new Futbolista();
		Object a = new Object();
		
		try {
			c.equals(a);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~: " + c.values());
			
			
		// PASO 1: Conexi�n al Server de MongoDB Pasandole el host y el puerto
			MongoClient mongoClient = new MongoClient("localhost", 27017);

		// PASO 2: Conexi�n a la base de datos
			DB db = mongoClient.getDB("Futbol");

		// PASO 3: Obtenemos una coleccion para trabajar con ella
			DBCollection collection = db.getCollection("Futbolistas");
			b = new Futbolista((BasicDBObject) collection.findOne());
			//b.equals(futbolista);
			System.out.print("//////////////////////////////////////////// >>> :" + b);

		// PASO 4: CRUD (Create-Read-Update-Delete)

			// PASO 4.1: "CREATE" -> Metemos los objetos futbolistas (o documentos en Mongo) en la coleccion Futbolista
			for (Futbolista fut : futbolistas) {
				collection.insert(fut.toDBObjectFutbolista());
			}

			// PASO 4.2.1: "READ" -> Leemos todos los documentos de la base de datos
			int numDocumentos = (int) collection.getCount();
			System.out.println("N�mero de documentos en la colecci�n Futbolistas: " + numDocumentos + "\n");

			// Busco todos los documentos de la colecci�n y los imprimo
			DBCursor cursor = collection.find();
			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next().toString());
				}
			} finally {
				cursor.close();
			}

			// PASO 4.2.2: "READ" -> Hacemos una Query con condiciones (Buscar Futbolistas que sean delanteros) y lo pasamos a un objeto Java
			System.out.println("\nFutbolistas que juegan en la posici�n de Delantero");
			DBObject query = new BasicDBObject("demarcacion", new BasicDBObject("$regex", "Delantero"));
			cursor = collection.find(query);
			try {
				while (cursor.hasNext()) {
					Futbolista futbolistaa = new Futbolista((BasicDBObject) cursor.next());
					System.out.println(futbolistaa.toString());
				}
			} finally {
				cursor.close();
			}
			
			

			// PASO 4.3: "UPDATE" -> Actualizamos la edad de los jugadores. Sumamos 100 a�os a los jugadores que tengan mas de 30 a�os
			DBObject find = new BasicDBObject("edad", new BasicDBObject("$gt", 30));
			DBObject updated = new BasicDBObject().append("$inc", new BasicDBObject().append("edad", 100));
			collection.update(find, updated, false, true);

			// PASO 4.4: "DELETE" -> Borramos todos los futbolistas que sean internacionales (internacional = true)
			DBObject findDoc = new BasicDBObject("internacional", true);
			collection.remove(findDoc);

		// PASO FINAL: Cerrar la conexion
			mongoClient.close();
			
			
		} catch (UnknownHostException ex) {
			System.out.println("Exception al conectar al server de Mongo: " + ex.getMessage());
		}
		
		return b.toDBObjectFutbolista();
	}

}
