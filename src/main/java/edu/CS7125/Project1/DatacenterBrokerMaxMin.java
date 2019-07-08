package edu.CS7125.Project1;

import java.util.ArrayList;

import org.cloudbus.cloudsim.DatacenterBroker;

class DatacenterBrokerMaxMin extends DatacenterBroker {

    public DatacenterBrokerMaxMin(String name) throws Exception {
        super(name);
    }

    @Override
    public void startEntity() {
        // setup all the cloudlet to VM bindings to support Max-Min scheduling
        double[][] executionTimes = new double[cloudletList.size()][vmList.size()];

        // build execution time matrix
        for (int i=0; i < cloudletList.size(); i++) {
            for (int j=0; j < vmList.size(); j++) {
                executionTimes[i][j] = cloudletList.get(i).getCloudletLength() / vmList.get(j).getMips();
            }
        }

        // loops through all cloudlets determining VMs to bind to

        ArrayList<Integer> scheduled = new ArrayList<>();
        
        while (scheduled.size() < cloudletList.size()) {
            // find task with max time to execute
            double maxExecutionTime = 0;
            double minExecutionTime = Double.MAX_VALUE;
            int currentCloudlet = 0;
            int currentVm = 0;

            for (int i=0; i < cloudletList.size(); i++) {
                if (!scheduled.contains(i)) {
                    for (int j=0; j < vmList.size(); j++) {
                        if (maxExecutionTime < executionTimes[i][j]) {
                            currentCloudlet = i;
                            maxExecutionTime = executionTimes[i][j];
                        }
                    }
                }
            }

            // have cloudlet with max execution time... now find resource with least
            for (int i=0; i < cloudletList.size(); i++) {
                if (!scheduled.contains(i)) {
                    for (int j=0; j < vmList.size(); j++) {
                        if (minExecutionTime > executionTimes[i][j]) {
                            currentVm = j;
                            minExecutionTime = executionTimes[i][j];
                        }
                    }
                }
            }

            // assign task with max execution time to resource with min execution time
            cloudletList.get(currentCloudlet).setVmId(vmList.get(currentVm).getId());
            scheduled.add(currentCloudlet);

            // update the completion times
            for (int i=0; i < cloudletList.size(); i++) {
                if (!scheduled.contains(i)) {
                    executionTimes[i][currentVm] += cloudletList.get(currentCloudlet).getCloudletLength() / vmList.get(currentVm).getMips(); 
                }
            }
        }

        // continue starting entity
        super.startEntity();
    }
}