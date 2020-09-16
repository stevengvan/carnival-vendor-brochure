
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner input = new Scanner(System.in); //for universal input

        cll map = new cll(); //map of vendors
        btree brochure = new btree(); //list to all vendors
        map.create_path(); //create first path for vendors
        int path_count = 1; //total # of paths


        //begin adding in vendors
        String decision = new String(); //decision for prompts
        do{
            vendor new_vendor = null; //vendor to fill in info

            //display all paths made
            System.out.println("Paths made:");
            for(int i = 0; i < path_count; ++i)
                System.out.println("-Path " + (i+ 1) + "-");

            //choose a path to work on
            int a_path = 0; //path to work on
            System.out.print("Which path to work in: ");
            a_path = input.nextInt();

            //select vendor format to add
            int choice = 0; //choice of vendor
            System.out.println("\nWhat kind of vendor do you want to add:"
                    + "\n1) Entertainment"
                    + "\n2) Food"
                    + "\n3) Games");
            choice = input.nextInt();

            map.display_path(a_path); //display all existing paths

            //info for vendor
            String name = new String();
            String type = new String();
            String desc = new String();
            int location = 0;

            //prompt input for vendor info
            System.out.print("\nWhere do you want to place the vendor: ");
            location = input.nextInt(); input.nextLine();
            System.out.print("Enter name of vendor: ");
            name = input.nextLine();
            System.out.print("Enter type of vendor: ");
            type = input.nextLine();
            System.out.print("Enter vendor description: ");
            desc = input.nextLine();

            //adding entertainment vendor
            if(choice == 1){
                String time = new String();
                System.out.print("Enter a time the event starts: ");
                time = input.nextLine();
                new_vendor = new entertain(time, name, type, desc, location);
            }

            //adding food vendor
            if(choice == 2)
                new_vendor = new food(name, type, desc, location);

            //adding carnival (game) vendor
            if(choice == 3)
                new_vendor = new carnival(name, type, desc, location);

            new_vendor.display(); //display filled vendor info

            //add vendor to path within map
            map.add_vendor(new_vendor, a_path);
            map.display_path(a_path);

            brochure.insert(new_vendor);//add vendor to brochure

            //prompt to create a new path of vendors
            System.out.print("\nCreate a new path (y/n): ");
            decision = input.next();
            if(decision.equals("y")){
                map.create_path(); //create new path
                path_count += 1; //add path to count
            }

            //prompt to modify a vendor
            System.out.print("\nModify a vendor (y/n): ");
            decision = input.next();
            if(decision.equals("y")) {
                //display all paths made
                System.out.println("\nPaths made:");
                for (int i = 0; i < path_count; ++i)
                    System.out.println("-Path " + (i + 1) + "-");
                //request to input a path to work in
                System.out.print("\nEnter a path ");
                int path = input.nextInt();

                //display path given the path number
                if(map.display_path(path)){
                    //request input of vendor # to modify
                    System.out.print("\nEnter a vendor#: ");
                    int find = input.nextInt();
                    //modify the vendor given the vendor #
                    if(!map.modify_vendor(a_path, find))
                        System.out.println("Could not modify given vendor"); //failed to modify given vendor
                }
            }

            //prompt to look for a vendor
            System.out.print("\nFind a vendor (y/n): ");
            decision = input.next(); input.nextLine();
            if(decision.equals("y")) {
                //request name of vendor
                String find = new String();
                System.out.print("\nEnter a name to look for: ");
                find = input.nextLine();

                //look for given vendor name
                if(map.retrieve(find) ==0)
                    System.out.println("Could not find vendor"); //failed to find vendor
            }

            //prompt to delete a vendor
            System.out.print("\nDelete a vendor (y/n): ");
            decision = input.next();
            if(decision.equals("y")){
                //display all paths made
                System.out.println("\nPaths made:");
                for(int i = 0; i < path_count; ++i)
                    System.out.println("-Path " + (i+ 1) + "-");
                //request path #
                System.out.print("\nEnter a path ");
                int path = input.nextInt();

                int find = 0;
                //request vendor # to delete
                if(map.display_path(path)) {
                    System.out.print("\nEnter a vendor#: ");
                    find = input.nextInt();
                }

                //delete vendor given the vendor #
                if(map.delete_vendor(path, find) != 0)
                    map.display_path(path);
                else System.out.println("Could not find vendor"); //failed to delete given vendor
            }

            //ask if user wants a new vendor
            System.out.print("\nEnter another vendor (y/n): ");
            decision = input.next();

        } while(decision.equals("y"));

        //display all paths and vendors
        //map.display_all();

        //display brochure
        brochure.display();
        input.nextLine();
        do{
            //prompt to search for a name
            System.out.print("\nFind a vendor name (y/n): ");
            decision = input.nextLine();
            if(decision.equals("y")){
                System.out.print("Enter a name to look for: ");
                String find = new String(input.nextLine());
                brochure.retrieve(find);
            }

            //prompt to search for a common type
            System.out.print("\nFind a vendor type (y/n): ");
            decision = input.nextLine();
            if(decision.equals("y")){
                System.out.print("Enter a name to look for: ");
                String find = new String(input.nextLine());
                brochure.retrieve_all(find);
            }

            //ask to try again
            System.out.print("\nTry again (y/n)");
            decision = input.nextLine();
        } while(decision.equals("y"));
    }
}