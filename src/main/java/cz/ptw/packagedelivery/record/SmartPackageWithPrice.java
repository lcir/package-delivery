package cz.ptw.packagedelivery.record;

/**
 * Main data record, with weight and postCode
 */
public final record SmartPackageWithPrice(Double weight, Integer postCode, Double price){
}
