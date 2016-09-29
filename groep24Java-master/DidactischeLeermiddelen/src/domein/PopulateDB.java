package domein;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import repository.GenericDaoJpa;
import repository.CurricularDaoJpa;

public class PopulateDB {

    public void run() {
        GenericDaoJpa materialdao = new GenericDaoJpa(Material.class);
        GenericDaoJpa.startTransaction();
        Material wereldbol = new Material("Wereldbol", "wereldbol");
        wereldbol.setImagepath("file:/C:/Users/Sofie/Pictures/wereldbol.png");
        materialdao.insert(wereldbol);
        Material blanco_draaischijf  = new Material("Blanco Draaischijf", "met verschillende blanco schijven in hard papier");
        Material dobbelsteen_schatkist = new Material("Dobbelsteen schatkist", "doos met dobbelstenen");
        materialdao.insert(blanco_draaischijf);
        materialdao.insert(dobbelsteen_schatkist);        
        materialdao.insert(new Material("Rekenspelletjes-optellen en aftrekken", "spelbord op het opdrachtenboekje leggen > opdracht oplossen door het juiste cijfers van het spelbord op het juiste antwoord in het boekje te leggen"));
        CurricularDaoJpa curriculardao = new CurricularDaoJpa();
        curriculardao.insert(new Curricular("geschiedenis"));
        curriculardao.insert(new Curricular("wiskunde"));
        curriculardao.insert(new Curricular("aardrijkskunde"));
        curriculardao.insert(new Curricular("mens"));
        curriculardao.insert(new Curricular("maatschappij"));
        curriculardao.insert(new Curricular("biologie"));
        curriculardao.insert(new Curricular("wetenschap"));
        curriculardao.insert(new Curricular("techniek"));
        curriculardao.insert(new Curricular("fysica"));
        GenericDaoJpa targetAudiencedao = new GenericDaoJpa(TargetAudience.class);
        targetAudiencedao.insert(new TargetAudience("Lageronderwijs"));
        targetAudiencedao.insert(new TargetAudience("Kleuteronderwijs"));
        targetAudiencedao.insert(new TargetAudience("Middelbaaronderwijs"));
        GenericDaoJpa reservationdao = new GenericDaoJpa(Reservation.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");

        GregorianCalendar begin = new GregorianCalendar(2016, 05, 13);
        GregorianCalendar einde = new GregorianCalendar(2016, 05, 17);

        reservationdao.insert(new Reservation( blanco_draaischijf, 5, begin, einde, "elise.lapeirre.s3756@student.hogent.be"));
        
   
              Reservation r = new Reservation( wereldbol, 5, new GregorianCalendar(2016, 4, 9), new GregorianCalendar(2016, 4, 13), "elise.lapeirre@hogent.com");

              reservationdao.insert(r);
              
        GenericDaoJpa administratordao = new GenericDaoJpa(Administrator.class);
        administratordao.insert(new Administrator("Ineke Van Gaver", "ineke.vangaver@hogent.be", "paswoord",true));
        administratordao.insert(new Administrator("Olivier Rosseel","olivier.rosseel@hogent.be","test"));
        GenericDaoJpa.commitTransaction();
    }
}
