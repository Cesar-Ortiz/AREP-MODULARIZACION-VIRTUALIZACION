package edu.escuelaing.arep.dockerdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RoundRobin {

    final static RoundRobin instance=new RoundRobin();

    // 1. Definir mapa, key-ip, value-weight
    static Map<String,Integer> ipMap=new HashMap<>();
    static {
        ipMap.put("http://ec2-174-129-72-24.compute-1.amazonaws.com:8088/",1);
        ipMap.put("http://ec2-174-129-72-24.compute-1.amazonaws.com:8089/",1);
        ipMap.put("http://ec2-174-129-72-24.compute-1.amazonaws.com:8090/",1);
    }

    //   Integer sum=0;
    Integer  pos = 0;

    public String getRobin(){
        Map<String,Integer> ipServerMap=new ConcurrentHashMap<>();
        ipServerMap.putAll(ipMap);

        // 2. Saca la llave y ponla en el set
        Set<String> ipset=ipServerMap.keySet();

        // 3. poner el conjunto en la lista y sacar la lista en un bucle
        ArrayList<String> iplist=new ArrayList<String>();
        iplist.addAll(ipset);

        String serverName=null;

        // 4. Defina el valor de un bucle, si es mayor que el establecido, comience desde 0
        synchronized(pos){
            if (pos>=ipset.size()){
                pos=0;
            }
            serverName=iplist.get(pos);
            // Encuesta +1
            pos ++;
        }
        return serverName;

    }



    public static  RoundRobin getInstance() {
        return instance;
    }
}

