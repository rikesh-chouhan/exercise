package org.prep.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.prep.example.model.ShippingPkg;

public class Shipping {
    List<ShippingPkg> sortedShippingPkgs;
    Comparator<ShippingPkg> comparator;

    public Shipping() {
        sortedShippingPkgs = new ArrayList();
        comparator = new PkgComparator();
    }

    public synchronized void addPackages(List<ShippingPkg> pkgArray) {
        synchronized (sortedShippingPkgs) {
            sortedShippingPkgs.addAll(pkgArray);
            prepData();
        }
    }

    private void prepData() {
        Collections.sort(sortedShippingPkgs, comparator);
    }

    public synchronized ShippingPkg nextPackage() {
        synchronized (sortedShippingPkgs) {
            if (sortedShippingPkgs.size() > 0) {
                return sortedShippingPkgs.remove(0);
            }
        }
        return null;
    }

    class PkgComparator implements Comparator<ShippingPkg> {

        @Override
        public int compare(ShippingPkg o, ShippingPkg t1) {
            if (o.priority == t1.priority) {
                return t1.id.compareTo(o.id);
            } else if (o.priority == ShippingPkg.Priority.HIGH) {
                return -1;
            } else if (t1.priority == ShippingPkg.Priority.HIGH) {
                return 1;
            } else {
                // should not reach here
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            List<ShippingPkg.Priority> priorities = Arrays.asList(ShippingPkg.Priority.values());
            List<ShippingPkg> packages = new ArrayList();
            for (String entry : args) {
                String array[] = entry.split(",");
                ShippingPkg pkg = new ShippingPkg();
                pkg.id = Integer.parseInt(array[0]);
                for (ShippingPkg.Priority priority: priorities) {
                    if ((""+priority.ordinal()).equalsIgnoreCase(array[1])) {
                        pkg.priority = priority;
                        break;
                    }
                }
                if (pkg.priority == null) {
                    pkg.priority = ShippingPkg.Priority.LOW;
                }
                System.out.println("priority: " + pkg.priority);
                packages.add(pkg);
            }

            Shipping shipping = new Shipping();
            shipping.addPackages(packages);
            ShippingPkg pkg;
            while ((pkg = shipping.nextPackage()) != null) {
                System.out.println("package priority: " + pkg.priority.name() + ", id: " + pkg.id);
            }
        } else {
            System.out.println("No args provided terminating");
        }
    }
}
