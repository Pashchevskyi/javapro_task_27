package ua.ithillel.lms.coffee.order;

public class Order {

  int id;
  String customerName;

  public Order(int id, String customerName) {
    this.id = id;
    this.customerName = customerName;
  }

  public int getId() {
    return id;
  }

  public String getCustomerName() {
    return customerName;
  }
}
