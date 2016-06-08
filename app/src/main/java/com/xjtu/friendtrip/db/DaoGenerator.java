package com.xjtu.friendtrip.db;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class DaoGenerator {
	 public static void main(String[] args) throws Exception {
	        int version = 1;
	        Schema schema = new Schema(version,"com.xjtu.friendtrip.db");
	        addTrace(schema);
	        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "src/main/java");
	    }

	    private static void addTrace(Schema schema) {
	        Entity trace = schema.addEntity("DBTrace");
	        trace.addIdProperty();
	        trace.addStringProperty("title");
	        trace.addStringProperty("time");
	        trace.addIntProperty("auth");

	        Entity file = schema.addEntity("DBTraceFile");
	        file.setTableName("FILES");
	        file.addIdProperty();
	        file.addStringProperty("url");
	        file.addStringProperty("type");
	        file.addStringProperty("summary");
	        Property filePosition = file.addIntProperty("position").getProperty();
	        Property traceId = file.addLongProperty("traceId").notNull().getProperty();
	        file.addToOne(trace,traceId);

	        ToMany traceToFiles = trace.addToMany(file,traceId);
	        traceToFiles.setName("files");
	        traceToFiles.orderAsc(filePosition);


	    }
}
