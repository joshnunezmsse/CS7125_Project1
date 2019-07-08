package edu.CS7125.Project1;

import org.cloudbus.cloudsim.DatacenterBroker;

class DatacenterBrokerShortestFirst extends DatacenterBroker {

    public DatacenterBrokerShortestFirst(String name) throws Exception {
        super(name);
    }
        
    @Override
    public void startEntity() {
        cloudletList.sort((a, b) -> a.getCloudletLength() < b.getCloudletLength() ? -1 : 1);
        super.startEntity();
    }
}