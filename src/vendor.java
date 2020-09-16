import java.util.Scanner;

//input class (generic)
class Util{
    protected Scanner input;
    public Util(){input = new Scanner(System.in);}
}

//abstract vendor class
abstract class vendor extends Util {
    protected String name;
    protected String type;
    protected String desc; //description of vendor
    protected int location; //location of vendor (array)

    //default constructor
    public vendor() {
        this.name = new String("EMPTY");
        this.type = new String("EMPTY");
        this.desc = new String("EMPTY");
        this.location = 0;
    }

    //overridden constructor
    public vendor(String a_name, String a_type, String a_desc, int a_loc) {
        this.name = new String(a_name);
        this.type = new String(a_type);
        this.desc = new String(a_desc);
        this.location = a_loc;
    }

    //display name only
    public boolean display_name() {
        System.out.println(this.name);
        return true;
    }
    //check if name matches vendor's name
    public boolean check(String match) {
        return this.name.equals(match);
    }
    //retrieve location of vendor
    public int get_area(){return this.location;}

    //abstract member functions
    abstract  public boolean display();
    abstract public boolean modify();
}

//entertainment vendor
class entertain extends vendor{
    private String time;

    //default constructor
    public entertain(){
        super(); //parent default constructor
        this.time = new String("EMPTY");
    }

    //overridden constructor
    public entertain(String a_time, String event, String a_type, String a_desc, int a_loc){
        super(event, a_type, a_desc, a_loc); //parent overridden constructor
        this.time = new String(a_time);
    }

    //display vendor;
    public boolean display(){
        System.out.println("\nVendor: " + super.name +
                "\nType: " + super.type +
                "\nDescription:\n" + super.desc +
                "\nTime: " + this.time +
                "\nLocation:" + super.location);
        return true;
    }

    //modify vendor
    public boolean modify(){
        //modify name and type
        System.out.print("Enter new name: ");
        super.name = input.nextLine();
        System.out.print("Enter new type: ");
        super.type = input.nextLine();

        String choice = new String();

        //modify time of event
        System.out.print("Modify the time of event (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")){
            System.out.print("New time: ");
            this.time = input.nextLine();
        }

        //modify description of event
        System.out.print("Modify the description (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")){
            System.out.print("New description:\n");
            super.desc =  input.nextLine();
        }

        //modify location of event
        System.out.print("Modify the location (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")) {
            System.out.print("New location: ");
            super.location = input.nextInt();
        }

        return true;
    }
}

//food vendor
class food extends vendor{
    private String menu[];
    private int size;

    //default constructor
    public food(){
        super(); //parent constructor

        //create menu
        System.out.print("How many items in the menu: ");
        this.size = input.nextInt();
        this.menu = new String[this.size];
        for (int i = 0; i < this.size; ++i)
            this.menu[i] = "EMPTY";
    }

    //overridden constructor
    public food(String event, String a_type, String a_desc, int a_loc){
        super(event, a_type, a_desc, a_loc); //parent overridden constructor

        //create menu
        System.out.print("How many items in the menu: ");
        this.size = input.nextInt(); input.nextLine();
        this.menu = new String[this.size];
        //add items to menu
        for (int i = 0; i < this.size; ++i){
            System.out.print("Enter food for item #" + i + ": ");
            this.menu[i] = input.nextLine();
        }
    }

    //display vendor;
    public boolean display(){
        System.out.println("\nVendor: " + this.name +
                "\nType: " + this.type +
                "\nDescription:\n" + super.desc +
                "\nLocation: " + super.location +
                "\nMenu: ");
        for(int i = 0; i < this.size; ++i)
            System.out.println(i + ") " + this.menu[i]);

        return true;
    }

    //modify vendor
    public boolean modify(){
        //modify name and type
        System.out.print("Enter new name: ");
        super.name = input.nextLine();
        System.out.print("Enter new type: ");
        super.type = input.nextLine();

        String choice = new String(); //decision

        //modify menu
        System.out.print("Modify the menu (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")){
            System.out.print("How many items in menu: ");
            this.size = input.nextInt();
            this.menu = new String[this.size];
            for(int i = 0; i < this.size; ++i){
                System.out.println( "Item #" + i + ") ");
                this.menu[i] = input.nextLine();
            }
        }

        //modify description of event
        System.out.print("Modify the description (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")){
            System.out.print("New description:\n");
            super.desc =  input.nextLine();
        }

        //modify location of event
        System.out.print("Modify the location (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")) {
            System.out.print("New location: ");
            super.location = input.nextInt();
        }

        return true;
    }
}

//carnival vendor
class carnival extends vendor{
    private String prize[];
    private int size;

    //default constructor
    public carnival(){
        super(); //parent default constructor
        //create prize list
        System.out.println("How many prizes are there: ");
        this.size = input.nextInt();
        this.prize = new String[this.size];
        for(int i = 0; i < this.size; ++i){
            this.prize[i] = "EMPTY";
        }
    }

    //overridden constructor
    public carnival(String event, String a_type, String a_desc, int a_loc){
        super(event, a_type, a_desc, a_loc); //parent overridden constructor
        //create prize list
        System.out.print("How many prizes are there: ");
        this.size = input.nextInt(); input.nextLine();
        this.prize = new String[this.size];
        //add to prize list
        for(int i = 0; i < this.size; ++i){
            System.out.print("Prize #" + i + ": ");
            this.prize[i] = input.nextLine();
        }
    }

    //display vendor;
    public boolean display(){
        System.out.println("\nVendor: " + this.name +
                "\nType: " + this.type +
                "\nDescription:\n" + super.desc +
                "\nLocation: " + super.location);
        return true;
    }

    //modify vendor
    public boolean modify(){
        //modify name and type
        System.out.print("Enter new name: ");
        super.name = input.nextLine();
        System.out.print("Enter new type: ");
        super.type = input.nextLine();

        String choice = new String(); //decision

        //modify description
        System.out.print("Modify the description (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")){
            System.out.print("New description:\n");
            super.desc =  input.nextLine();
        }

        //modify location
        System.out.print("Modify the location (y/n): ");
        choice = input.nextLine();
        if(choice.equals("y")) {
            System.out.print("New location: ");
            super.location = input.nextInt();
        }

        return true;
    }
}