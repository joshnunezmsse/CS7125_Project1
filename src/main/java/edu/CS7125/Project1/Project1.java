package edu.CS7125.Project1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class Project1 {

    static int nextBrokerNumber = 0;
    static int nextDatacenterNumber = 0;
    static int nextVMId = 0;
    static int nextCloudletId = 0;
    static int nextHostId = 0;

    public static void main(String[] args) {
        CloudSim.init(1, Calendar.getInstance(), false);

        Datacenter dc0 = createDatacenter();
        MyBroker broker1 = null;
        String brokerType = "";

        if (args.length > 0) {
            brokerType = args[0];
        }

        broker1 = BrokerWrapper.getInstance(brokerType);

        HashMap<String, String> vmOptions = new HashMap<String, String>();
        ArrayList<Vm> vmList = new ArrayList<>();
        vmOptions.put("mips", "300");
        vmList.add(createVM(broker1.getId(), vmOptions, new CloudletSchedulerSpaceShared()));
        vmOptions.put("mips", "600");
        vmList.add(createVM(broker1.getId(), vmOptions, new CloudletSchedulerSpaceShared()));
        vmOptions.put("mips", "400");
        vmList.add(createVM(broker1.getId(), vmOptions, new CloudletSchedulerSpaceShared()));
        
        HashMap<String, String> cloudletOptions = new HashMap<String, String>();
        ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>(10);
        long[] lengths=new long[]{18000, 42000, 21000, 32000, 20000, 15000, 10000, 30000, 40000, 50000};
        for (int i=0; i < 10; i++) {
            cloudletOptions.put("length", String.valueOf(lengths[i]));
            cloudletList.add(createCloudlet(broker1.getId(), -1, cloudletOptions, new UtilizationModelFull()));
        }

        broker1.submitVmList(vmList);
        broker1.submitCloudletList(cloudletList);

        CloudSim.startSimulation();
        List<Cloudlet> output1 = broker1.getCloudletReceivedList();
        CloudSim.stopSimulation();
        
        Log.printLine("First Come First Serve");
        printCloudletList(output1);
        
    }
    
    private static Vm createVM(int brokerId, Map<String, String> options, CloudletScheduler scheduler) {
        double mips = 1000;
        int ram = 512;
        
        if (options.containsKey("mips")) {
            mips = Double.parseDouble(options.get("mips"));
        }

        if (options.containsKey("ram")) {
            ram = Integer.parseInt(options.get("ram"));
        }
        
        Log.printLine(String.format("VM #%d options: MIPS(%f) RAM(%d)", nextVMId, mips, ram));
        
        return new Vm(
            nextVMId++,
            brokerId,
            mips,
            1,
            ram,
            1000,
            10000,
            "Xen",
            new CloudletSchedulerTimeShared()
        );
    }
    
    private static Cloudlet createCloudlet(int brokerId, int vmId, Map<String, String> options, UtilizationModel model) {
        long length = 40000;

        if (options.containsKey("length")) {
            length = Long.parseLong(options.get("length"));
        }

        Cloudlet retVal = new Cloudlet(
                                nextCloudletId++,
                                length,
                                1,
                                300,
                                300,
                                model,
                                model,
                                model
                            );
        
        retVal.setUserId(brokerId);
        
        if (vmId != -1) {
            retVal.setVmId(vmId);
        }
        
        return retVal;
    }

    private static Datacenter createDatacenter() {
        Datacenter dc = null;
        
        ArrayList<Pe> peList = new ArrayList<>();
        peList.add(new Pe(0, new PeProvisionerSimple(1000)));
        //peList.add(new Pe(1, new PeProvisionerSimple(1000)));
        //peList.add(new Pe(2, new PeProvisionerSimple(1000)));
        
        ArrayList<Host> hostList = new ArrayList<>();
        hostList.add(new Host(
                nextHostId++,
                new RamProvisionerSimple(8 * 1024),
                new BwProvisionerSimple(10000),
                1000000,
                peList,
                new VmSchedulerSpaceShared(peList)
            ));

        hostList.add(new Host(
            nextHostId++,
            new RamProvisionerSimple(8 * 1024),
            new BwProvisionerSimple(10000),
            1000000,
            new ArrayList<Pe>(1) {{ add(new Pe(1, new PeProvisionerSimple(1000))); }},
            new VmSchedulerSpaceShared(peList)
        ));
        
        hostList.add(new Host(
            nextHostId++,
            new RamProvisionerSimple(8 * 1024),
            new BwProvisionerSimple(10000),
            1000000,
            new ArrayList<Pe>(1) {{ add(new Pe(2, new PeProvisionerSimple(1000))); }},
            new VmSchedulerSpaceShared(peList)
        ));

        DatacenterCharacteristics chars = new DatacenterCharacteristics(
                "x86",
                "Linux",
                "Xen",
                hostList,
                6,
                3,
                0.05,
                0.001,
                0.0
        );
        
        try {
            dc = new Datacenter("Datacenter"+(nextDatacenterNumber++), chars, new VmAllocationPolicySimple(hostList), Arrays.asList(), 0);
        } catch (Exception ex) {
            Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dc;
    }
    
    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        String format = "%15s\t%10s\t%15s\t%10s\t%10s\t%10s\t%10s\t%18s";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine(String.format(format, "Cloudlet ID", "STATUS", "Data center ID", "VM ID", "Time", "Start Time", "Finish Time", "Submission Time"));

        double throughput = 0.0;
        double totaltime = 0.0;

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);

            totaltime += cloudlet.getActualCPUTime();

            if (cloudlet.getActualCPUTime() > throughput) {
                throughput = cloudlet.getActualCPUTime();
            }

            Log.printLine(String.format(format, cloudlet.getCloudletId(),
                                      (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS ? "SUCCESS": "FAILED"),
                                      cloudlet.getResourceName(cloudlet.getResourceId()), 
                                      cloudlet.getVmId(), 
                                      dft.format(cloudlet.getActualCPUTime()),
                                      dft.format(cloudlet.getExecStartTime()),
                                      dft.format(cloudlet.getFinishTime()),
                                      dft.format(cloudlet.getSubmissionTime())));
        }

        Log.printLine();
        Log.printLine(String.format("Throughput: %.2f, Turnaround: %.2f", throughput, (totaltime/size)));
    }

}