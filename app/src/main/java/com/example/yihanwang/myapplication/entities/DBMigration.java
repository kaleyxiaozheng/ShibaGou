package com.example.yihanwang.myapplication.entities;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class DBMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.i("database", "oldversion=" + oldVersion + ", new version=" + newVersion);
        if (oldVersion == 0 && newVersion == 2) {
            RealmSchema schema = realm.getSchema();
            RealmObjectSchema imageInfo = schema.get("ImageInfo");
            imageInfo.setRequired("name", false);
            imageInfo.setRequired("commonName", false);
            imageInfo.setRequired("guid", false);
            imageInfo.setRequired("kingdom", false);
            imageInfo.setRequired("family", false);
            imageInfo.setRequired("rank", false);
            imageInfo.setRequired("imageUrl", false);
            imageInfo.setRequired("thumbnailUrl", false);
            imageInfo.setRequired("Locations", false);
            if (!imageInfo.hasField("description")) {
                imageInfo.addField("description", String.class);
            }
            imageInfo.addField("id_tmp", double.class).transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setDouble("id_tmp", obj.getDouble("id"));
                }
            });
            imageInfo.removeField("id");
            imageInfo.renameField("id_tmp", "id");

            imageInfo.removeField("longtitude").addField("longtitude", double.class);
            imageInfo.removeField("latitude").addField("latitude", double.class);

            imageInfo.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj){
                    obj.setString("description", obj.getString("desc"));
                }
            });
            imageInfo.removeField("desc");

//            imageInfo.addField("lat_tmp", double.class).transform(new RealmObjectSchema.Function() {
//                @Override
//                public void apply(DynamicRealmObject obj) {
//                    if(!obj.getString("latitude").isEmpty()){
//                        try {
//                            obj.setDouble("lat_tmp", Double.parseDouble(obj.getString("latitude")));
//                        }catch(NumberFormatException e){
//
//                        }
//                    }
//                }
//            });
//            imageInfo.addField("latitude", double.class);
//            imageInfo.renameField("lat_tmp", "latitude");
//
//            imageInfo.addField("lon_tmp", double.class).transform(new RealmObjectSchema.Function() {
//                @Override
//                public void apply(DynamicRealmObject obj) {
//                    if(!obj.getString("longtitude").isEmpty()) {
//                        try {
//                            obj.setDouble("lon_tmp", Double.parseDouble(obj.getString("longtitude")));
//                        }catch(NumberFormatException e){
//
//                        }
//                    }
//                }
//            });
//            imageInfo.addField("longtitude", double.class);
//            imageInfo.renameField("lon_tmp", "longtitude");
        }
    }
}
