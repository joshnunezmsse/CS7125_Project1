package edu.CS7125.Project1;

import java.util.List;

import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

interface MyBroker {
    int getId();
    void submitVmList(List<? extends Vm> list);
    void submitCloudletList(List<? extends Cloudlet> list);
    List<Cloudlet> getCloudletReceivedList();
}

class BrokerWrapper implements MyBroker {

    private DatacenterBroker mBroker;

    public static MyBroker getInstance(String type) {
        MyBroker retBroker = null;
    
        Log.printLine(String.format("Creating broker %s", type));
        
        try {
            if (type.equalsIgnoreCase("shortestfirst")) {
                retBroker = new BrokerWrapper(new DatacenterBrokerShortestFirst("BrokerShortestFirst"));
            } else if (type.equalsIgnoreCase("minmin")) {
                retBroker = new BrokerWrapper(new DatacenterBrokerMinMin("BrokerMinMin"));
            } else if (type.equalsIgnoreCase("maxmin")) {
                retBroker = new BrokerWrapper(new DatacenterBrokerMaxMin("BrokerMaxMin"));
            } else if (type.equalsIgnoreCase("suffrage")) {
                retBroker = new BrokerWrapper(new DatacenterBrokerSufferage("BrokerSuffrage"));
            } else {
                retBroker = new BrokerWrapper(new DatacenterBroker("BrokerDefault"));
            }
        } catch (Exception e) {
            Log.printLine(String.format("Failed to create broker %s", type));
        }
    
        return retBroker;
    }

    BrokerWrapper(DatacenterBroker broker) {
        this.mBroker = broker;
    }
    
    @Override
    public int getId() {
        return this.mBroker.getId();
    }

    @Override
    public void submitVmList(List<? extends Vm> list) {
        this.mBroker.submitVmList(list);
    }

    @Override
    public void submitCloudletList(List<? extends Cloudlet> list) {
        this.mBroker.submitCloudletList(list);
    }

    @Override
    public List<Cloudlet> getCloudletReceivedList() {
        return this.mBroker.getCloudletReceivedList();
    }
    
}