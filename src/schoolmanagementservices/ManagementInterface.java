package schoolmanagementservices;

public interface ManagementInterface<T> {

    T add();

    void delete();

    void showAll();

    T findByNameOrID();
    
    void showDetailedInfo(T t);

    void update(String choice);
    
    void updateName(T t);

}
