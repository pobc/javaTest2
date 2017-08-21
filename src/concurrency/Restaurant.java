package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiang on 17/7/23.
 */
class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    public String toString() {
        return "Meal " + orderNum;
    }
}

class BusBoy implements Runnable {
    Restaurant restaurant;

    BusBoy(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this){
                    if (restaurant.waitPerson == null){
                        wait();
                    }
                }
                synchronized (this) {
                    System.out.println("Busboy here " + restaurant.meal);
                    wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Busboy exception");
            e.printStackTrace();
        }

    }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;

    public WaitPerson(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null || restaurant.waitPerson==null)
                        wait(); // ... for the chef to produce a meal
                }
                System.out.println("Waitperson got " + restaurant.meal);
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll(); // Ready for another
                    synchronized (restaurant.busBoy){
                        restaurant.busBoy.notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("WaitPerson interrupted");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;

    public Chef(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("a1");
                synchronized (this) {
                    while (restaurant.meal != null)
                        wait(); // ... for the meal to be taken
                }
                if (++count == 10) {
                    System.out.println("Out of food, closing");
                    restaurant.waitPerson =null;
                    restaurant.exec.shutdownNow();
                    return;
                }
                System.out.println("Order up! ");
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                //TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

public class Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);
    BusBoy busBoy = new BusBoy(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(busBoy);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
