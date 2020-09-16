//node for cll, represents a path
class cnode extends Util{
    private int path; //path within list
    private vendor list[]; //list of vendors in path
    private int l_size; //size of vendor list
    private cnode next;

    //default constructor
    public cnode(){
        this.path = 0;
        System.out.println("How many areas in this path: ");
        this.l_size = input.nextInt();
        this.list = new vendor[this.l_size];

        for(int i = 0; i < this.l_size; ++i)
            this.list[i] = null;
    }

    //add in path for constructor;
    public cnode(int a_path){
        //create spaces for path
        this.path = a_path;
        System.out.print("How many areas in this path: ");
        this.l_size = input.nextInt();
        //initialize vendor list
        this.list = new vendor[this.l_size];
        for(int i = 0; i < this.l_size; ++i)
            this.list[i] = null;
    }
    //add vendor to list
    public int add(int a_path, vendor to_add){
        //not the right path
        if(this.path != a_path)
            return 1;
        //space is occupied
        if(this.list[to_add.get_area()] != null)
            return 2;
            //empty space within area
        else{
            this.list[to_add.get_area()] = to_add;
            return 0;
        }
    }

    //check for a vendor with matching name
    public int find_vendor(String match){
        int count = 0; //count # of matching vendors
        //go through vendors
        for(int i = 0; i < this.l_size; ++i){
            if(this.list[i] != null && this.list[i].check(match)) {
                this.list[i].display();
                ++count;
            }
        }
        return count;
    }

    //modify vendor within node
    public int modify(int a_path, int find){
        //path does not match
        if(this.path != a_path)
            return 1;
        //index out of bound
        if(find >= this.l_size)
            return 2;
        else{
            this.list[find].modify();
            return 0;
        }
    }

    //delete vendor from space
    public boolean delete(int place){
        //vendor does not exist
        if(this.list[place] == null || place >= this.l_size) {
            System.out.println("Cannot delete an empty space");
            return false;
        }
        //vendor found within space, deleting vendor
        else{
            this.list[place] = null;
            return true;
        }
    }

    //display the node
    public boolean display(){
        //display path and vendors
        System.out.println("\n-Path #" + this.path + "-");
        for(int i = 0; i < this.l_size; ++i){
            //empty space
            if(this.list[i] == null)
                System.out.println("Lot " + i + ": Empty");
                //occupied space (has a vendor)
            else {
                System.out.print("Lot " + i + ": ");
                this.list[i].display_name();
            }
        }
        return true;
    }

    //check if path matches match
    public boolean p_match(int match){return (path == match);}
    //get node's next reference
    public cnode getnext(){return this.next;}
    //set next reference of node
    public void setnext(cnode connect){this.next = connect;}
}

class  cll{
    private int p_count; //# of paths
    private cnode rear; //rear of list
    private cnode front; //front of list

    //default constructor
    public cll() {
        this.p_count = 0;
        this.rear = this.front = null;
    }

    //create new path for map
    public int create_path(){
        this.p_count += 1; //add path to path count
        cnode temp = new cnode(this.p_count); //create new path with incremented path count
        //list is empty
        if(this.front == null) {
            this.front = temp;
            this.rear = this.front;
            return p_count;
        }
        //multiple items exist in list
        else{
            temp.setnext(this.front);
            this.rear.setnext(temp);
            this.front = this.rear.getnext();
            return p_count;
        }
    }

    //add vendor to the list (wrapper)
    public int add_vendor(vendor to_add, int a_path){
        //path is out of bound
        if(a_path == 0 || a_path > this.p_count){System.out.println("Path out of bound"); return 0;}
        return add_vendor(front, to_add, a_path);
    }
    //add a vendor to the list
    private int add_vendor(cnode front, vendor to_add, int a_path){
        if(front == null) return 0; //empty list
        //cannot place in occupied space within matching path
        if(front.add(a_path, to_add) == 2){
            System.out.println("Cannot place in occupied space");
            return 1;
        }
        //added vendor to empty lot
        if(front.add(a_path, to_add) == 0) {
            System.out.println("Added to space");
            return 1;
        }
        //reached the last path
        if(front == this.rear && !front.p_match(a_path)) {
            System.out.println("Could not find path");
            return 0;
        }
        //recursive call
        return add_vendor(front.getnext(), to_add, a_path);
    }

    //retrieve a vendor
    public int retrieve(String find){
        if(this.front == null) return 0; //empty list
        return retrieve(this.front, find);
    }
    private int retrieve(cnode front, String find){
        if(front == null) return 0; //empty list

        int check = front.find_vendor(find); //check path

        //found vendor within path
        if(check != 0)
            return check;
        //vendor not found
        if(front == this.rear && check == 0)
            return 0;

        return retrieve(front.getnext(), find); //recursive call
    }

    //modify a vendor within a path
    public boolean modify_vendor(int a_path, int find){
        if(this.front == null) return false; //empty list
        return modify_vendor(this.front, a_path, find);
    }
    private boolean modify_vendor(cnode front, int a_path, int find){
        if(front == null) return false; //empty list
        //path not found
        if(front == this.rear && !front.p_match(a_path)) {
            System.out.print("Path out of bound");
            return false;
        }
        //path found
        if(front.p_match(a_path)){
            //check if vendor # exists
            int check = front.modify(a_path, find);
            //vendor # does not exist
            if(check == 1 || check == 2) return false;
            //vendor # exist, modifying space
            if(check == 0)
                return true;
        }
        return modify_vendor(front.getnext(), a_path, find);
    }

    //delete a vendor within a path
    public int delete_vendor(int a_path, int find){
        if(this.front == null) return 0; //empty list
        return delete_vendor(this.front, a_path, find);
    }
    private int delete_vendor(cnode front, int a_path, int find){
        if(front == null) return 0; //empty list
        //path not found within list
        if(front == this.rear && !front.p_match(a_path)) return 0;
        //path found
        if(front.p_match(a_path)){
            //deletion is successful
            if(front.delete(find))
                return find;
                //vendor not found or cannot be deleted
            else return 0;
        }
        return delete_vendor(front.getnext(), a_path, find);
    }

    //display a path
    public boolean display_path(int find){
        if(this.front == null || find > this.p_count) return false; //empty list
        return display_path(this.front, find);
    }
    private boolean display_path(cnode front, int find){
        if(front == null) return false; //empty list
        //could not find find path
        if(front == this.rear && !front.p_match(find))
            return false;
        //path found, displaying entire vendor list
        if(front.p_match(find)){
            front.display();
            return true;
        }
        return display_path(front.getnext(), find);
    }

    //display all vendors of each path
    public int display_all(){
        if(this.front == null) return 0; //empty list
        return display_all(this.front);
    }
    private int display_all(cnode front){
        if(front == null) return 0; //empty list
        //reached end of list
        if(front == this.rear){
            front.display();
            return 1;
        }
        //display list of vendors in path
        front.display();
        return display_all(front.getnext());
    }
}