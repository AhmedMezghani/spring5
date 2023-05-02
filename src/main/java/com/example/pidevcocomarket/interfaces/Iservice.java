package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.BadWords;
import com.example.pidevcocomarket.entities.Boutique;
import com.example.pidevcocomarket.entities.Contract;
import com.example.pidevcocomarket.entities.Notification;
import com.example.pidevcocomarket.repositories.NotificationRepository;


import java.util.List;

public interface Iservice {
/////////Les fonctions pour Boutiques/////////////////////
    public Contract addContract(Contract c);
    public Contract updateContract (Contract c);
    public void removeContract (Long idContract);
    public List<Contract> retrieveAllContract();

    public Contract retrieveContract (Long  idContract);

/////////////Les fonctions pour Boutiques///////////////
    public Boutique addboutique (Boutique b);
    public Boutique updateBoutique ( Boutique b);
    public void removeBoutique(Integer idBoutique);
    public List<Boutique> retrieveAllBoutiques();

    public Boutique retrieveBoutique (Integer  idBoutique);
//////////////notifications//////////////////////

    public void saveNotification(Notification notification);
    public void setNotificationRepository(NotificationRepository notificationRepository);
    public Notification addnotifications(Notification notification) ;

    List<Notification> getAllNotifications();

///////////////////////////BadWords////////////////////////////////
public BadWords addword (BadWords b);

                             /*mohamed*/
    Boutique createNewBoutiquePourPromtion(Boutique boutique);
                            /*end mohamed*/
}
