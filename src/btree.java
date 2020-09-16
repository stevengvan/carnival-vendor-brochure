//node for linked list of duplicate names
class lnode extends Util{
    private vendor matching; //data of node
    private lnode next; //refers to node next to calling node

    //default constructor
    public lnode() {
        this.matching = null;
        this.next = null;
    }
    //constructor to add in first vendor
    public lnode(vendor to_add){
        this.matching = to_add;
        this.next = null;
    }

    //display vendor within node
    public int display() {
        //no vendor to display
        if(this.matching == null) return 0;
        //display vendor
        this.matching.display();
        return 1;
    }

    //compare vendors' name
    public int check_dup(vendor check) {return this.matching.name.compareTo(check.name);}
    public int check_dup(String check) {return this.matching.name.compareTo(check);}
    //compare vendors' type
    public int check_type(String check) {return this.matching.type.compareTo(check);}
    //get vendor of node
    public vendor get_vendor(){return this.matching;}

    public lnode getnext(){return this.next;} //get next node
    public void setnext(lnode connect){this.next = connect;} //set next node
}

//node for 2-3 tree (btree)
class bnode{
    private lnode head[]; //list of duplicate names with the first addition
    private bnode next[]; //children of node

    //default constructor
    public bnode() {
        this.head = new lnode[2];
        for (int i = 0; i < 2; ++i)
            this.head[i] = null;
        this.next = new bnode[3];
        for (int i = 0; i < 3; ++i)
            this.next[i] = null;
    }
    //constructor with vendor list to add
    public bnode(lnode to_add){
        this.head = new lnode[2];
        //add list of duplicate vendors to first index as first item
        this.head[0] = new lnode(to_add.get_vendor());
        this.head[1] = null;

        this.next = new bnode[3];
        for (int i = 0; i < 3; ++i)
            this.next[i] = null;
    }

    //insert a vendor into the node
    public int insert(vendor to_add){
        //duplicate added to linear linked list
        for (int i = 0; i < 2; ++i) {
            //fill up empty data
            if (this.head[i] == null) {
                this.head[i] = new lnode(to_add);
                //check if there are two items
                if (this.head[1] != null) {
                    //sort if second index is less than first
                    if (this.head[1].check_dup(this.head[0].get_vendor()) < 0) {
                        lnode temp = this.head[1];
                        this.head[1] = this.head[0];
                        this.head[0] = temp;
                        return 0;
                    }
                }
                else return 0;
            }
            //insert duplicate
            if (this.head[i] != null && this.head[i].check_dup(to_add) == 0) {
                insert(this.head[i], to_add);
                return 0;
            }
        }

        //if second element is not occupied, try sort
        if(this.head[1] != null) {
            //split data to nodes if third data is added
            if (this.head[0].check_dup(to_add) != 0 && this.head[1].check_dup(to_add) != 0)
                return 1;
        }
        return 0;
    }
    //insert to linked list if duplicate is found
    private lnode insert(lnode head, vendor to_add){
        //insertion at end of the list
        if(head == null){
            return new lnode(to_add);
        }
        //recursive call to get to the end of the list
        head.setnext(insert(head.getnext(), to_add));
        return head;
    }

    //compare vendor names
    public int compare_name(int index, String compare){
        if(this.head[index] == null) return 0;
        return this.head[index].check_dup(compare);
    }
    //compare vendor type
    public int compare_type(int index, String compare){
        if(this.head[index] == null) return -1;
        return this.head[index].check_type(compare);
    }

    //split node if data is filled
    public lnode split(bnode parent, vendor third){
        lnode median = null; //data to be pushed up
        lnode left = null; //smaller data
        lnode right = null; //largest data

        //first index is to be pushed
        if(this.head[0] != null && this.head[0].check_dup(third) < 0){
            median = new lnode(this.head[0].get_vendor()); //middle gets first data of node
            left = new lnode(third); //new entry is smallest
            right = new lnode(this.head[1].get_vendor()); //second data in node is largest
        }
        //second index is to be pushed
        if(this.head[1] != null && this.head[1].check_dup(third) > 0){
            median = new lnode(this.head[1].get_vendor()); //middle gets largest data of node
            left = new lnode(this.head[0].get_vendor()); //first data is lesser
            right = new lnode(third); //new entry is largest of the three
        }
        //third addition is to be pushed
        else{
            median = new lnode(third); //new entry is in-between first and second data of node
            //first data is smallest
            if(this.head[0] != null){left = new lnode(this.head[0].get_vendor());}
            //second data is largest
            if(this.head[1] != null){right = new lnode(this.head[1].get_vendor());}
        }

        //if parent node is not same node as node called from
        if(parent != this){
            //place largest in middle child
            if(parent.head[0].check_dup(right.get_vendor()) > 0){
                parent.next[0].head[0] = new lnode(left.get_vendor());
                parent.next[1].head[0] = new lnode(right.get_vendor());
            }
            //place smallest in middle child
            if(parent.head[0].check_dup(left.get_vendor()) < 0){
                parent.next[1].head[0] = new lnode(left.get_vendor());
                parent.next[2].head[0] = new lnode(right.get_vendor());
            }
        }
        //only one node exists in tree
        if(parent == this){
            parent.next[0] = new bnode(left);
            parent.next[2] = new bnode(right);
        }

        return median; //return middle data to be pushed up
    }

    //display vendors in all indices
    public int display() {
        int count = 0;
        //go through the two data if any exists
        for (int i = 0; i < 2; ++i) {
            if (this.head[i] != null)
                count = this.head[i].display();
        }
        return count;
    }
    //display vendors of an index
    public int display(int i) {
        if(this.head[i] != null)this.head[i].display();
        return 0;
    }

    //display duplicates
    public int display_duplicate(int i){
        int count = 0;
        if(this.head[i] != null && this.head[i].getnext() != null)
            count += display_duplicate(this.head[i].getnext());
        return count;
    }
    //recursive function to go through list of duplicates
    private int display_duplicate(lnode head){
        if(head == null) return 0; //reached the end of list
        int count = 0;
        head.display(); //display content
        ++count; //increment display count

        //recursive call
        count += display_duplicate(head.getnext());
        return count;
    }

    //check if bnode is full
    public boolean check_full(){
        //not full
        if(this.head[1] == null) return false;
            //is full
        else return true;
    }

    //check if node is a leaf
    public boolean is_leaf(){
        //reached a leaf
        if(this.next[0] == null && this.next[1] == null && this.next[2] == null)
            return true;
            //not a leaf
        else return false;
    }

    //return next pointers
    public bnode getnext(int index){
        if(index == 0) return this.next[0];
        if(index == 1) return this.next[1];
        if(index == 2) return this.next[2];
        else return null;
    }
}

//2-3 tree class
class btree {
    bnode root;

    public btree(){this.root = null;} //default constructor

    //insert a vendor into the tree
    public int insert(vendor to_add){
        //tree is empty, adding vendor as first item of tree
        if(this.root == null){
            this.root = new bnode();
            this.root.insert(to_add);
            return 0;
        }
        bnode parent = this.root; //set parent up for potential of pushed data from splitting
        insert(this.root, parent, to_add); //recursive call to reach a leaf
        return 0;
    }
    //recursive function to insert in tree
    private lnode insert(bnode root, bnode parent, vendor to_add) {
        lnode temp = null; //temporary data to hold pushed data from split
        if (root == null) return temp; //tree is empty

        //add duplicate into list of duplicates
        if(root.compare_name(0, to_add.name) == 0 || root.compare_name(1, to_add.name) == 0){
            root.insert(to_add);
            return temp;
        }

        //found leaf, adding new entry
        if (root.is_leaf()) {
            int check = root.insert(to_add); //attempt to insert
            //node is full, begin splitting
            if (check == 1){temp = root.split(parent, to_add);}
            return temp;
        }

        //vendor is less than the first index, move to left child
        if (root.compare_name(0, to_add.name) < 0) {
            parent = root.getnext(0);
            temp = insert(root.getnext(0), parent, to_add);
        }
        //vendor is less than the second, move to middle child
        if (root.compare_name(1, to_add.name) < 0) {
            parent = root.getnext(1);
            temp = insert(root.getnext(1), parent, to_add);
        }
        //vendor is greater than the second, move to third child
        if (root.compare_name(1, to_add.name) > 0) {
            parent = root.getnext(2);
            temp = insert(root.getnext(2), parent, to_add);
        }

        int check = 0;
        //attempt to insert pushed up data from split to node
        if(!root.check_full() && temp != null)
            check = root.insert(temp.get_vendor());
        //node is full, begin splitting
        if (check == 1) {
            if (root.check_full()){
                //get updated data to push
                temp = root.split(parent, temp.get_vendor());
                //splitting is happening at actual root of tree
                if(root == parent || root == this.root){
                    //insert pushed data to new root
                    bnode new_root = new bnode();
                    new_root.insert(temp.get_vendor());
                    this.root = new_root; //update root
                    return null;
                }
            }
        }

        return temp;
    }

    //display the entire tree
    public int display(){
        if(this.root == null) return 0; //tree is empty
        return display(this.root);
    }
    //recursive display function
    private int display(bnode root){
        if(root == null) return 0; //tree has reached end of subtree

        root.display(); //display node's content

        //traverse to next nodes
        display(root.getnext(0));
        display(root.getnext(1));
        display(root.getnext(2));

        return 0;
    }

    //retrieve a vendor with matching name
    public boolean retrieve(String find){
        if(this.root == null) return false; //tree is empty
        return retrieve(root, find);
    }
    //recursive retrieval function
    private boolean retrieve(bnode root, String find){
        if(root == null) return false; //end of the tree has been reached

        //display first data if matching
        if(root.compare_name(0, find) == 0) {
            root.display(0);
            root.display_duplicate(0);
            return true;
        }
        //display second data if matching
        if(root.compare_name(1, find) == 0) {
            root.display(1);
            root.display_duplicate(1);
            return true;
        }

        //traverse through the tree
        retrieve(root.getnext(0), find);
        retrieve(root.getnext(1), find);
        retrieve(root.getnext(2), find);
        return false;
    }

    //retrieve all vendors with same type
    public boolean retrieve_all(String find){
        if(this.root == null) return false; //tree is empty
        return retrieve_all(this.root, find);
    }
    //recursive function to retrieve all
    private boolean retrieve_all(bnode root, String find){
        if(root == null) return false; //end of the tree has been reached

        //display data if types are matching
        for(int i = 0; i < 2; ++i) {
            if (root.compare_type(i, find) == 0)
                root.display(i);
        }

        //traverse through tree
        retrieve_all(root.getnext(0), find);
        retrieve_all(root.getnext(1), find);
        retrieve_all(root.getnext(2), find);
        return true;
    }
}