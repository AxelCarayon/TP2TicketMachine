package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}
        
        @Test
        // S3 : On n'imprime pas le ticket si le montant inséré est insuffisant
        public void noPrintTicketIfNotEnoughMoney() {
            machine.insertMoney(40);
            assertFalse("La machine imprime un ticket alors que le montant est insuffisant",machine.printTicket());
        }
        
        @Test
        //S4 : On imprime le ticket si le montant inséré est suffisant
        public void printTicketIfEnoughMoney() {
            machine.insertMoney(PRICE);
            assertTrue("La machine n'imprime pas le ticket alors le montant est suffisant", machine.printTicket());
        }
        
        @Test
        //S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
        public void decrementBalanceWhenPrintingTicket(){
            int reste = 10;
            machine.insertMoney(PRICE+reste);
            machine.printTicket();
            assertEquals("Le prix du ticket imprimé n'as pas été décrémenté de la balance",reste,machine.getBalance());
        }
        
        @Test
        //S6 : Le montant colecté est mis à jour quand on imprime un ticket (pas avant)
        public void priceAddedToTotalWhenPrinted(){
            int ancienTotal = machine.getTotal();
            machine.insertMoney(PRICE);
            assertEquals("Le montant total a été mis à jour avant l'impression du ticket",ancienTotal,machine.getTotal());
            machine.printTicket();
            int nouveauTotal = ancienTotal + PRICE;
            assertEquals("Le montant total n'as pas été mis correctement à jour après impression du ticket",nouveauTotal,machine.getTotal());
        }
        
        @Test
        //S7 : refund() rend correctement la monnaie
        public void refundIsCorrect(){
            int montantA = 10;
            int montantB = 20;
            int montantC = 40;
            int montantInsereTotal = montantA + montantB + montantC;
            machine.insertMoney(montantA);
            machine.insertMoney(montantB);
            machine.insertMoney(montantC);
            assertEquals("Le montant rendu est différent du montant inséré",montantInsereTotal, machine.refund());
        }

        @Test
        //S8 : refund() remet la balance à zéro
        public void refundSetBalanceToZero(){
            int montantA = 10;
            int montantB = 20;
            int montantC = 40;
            int montantInsereTotal = montantA + montantB + montantC;
            machine.insertMoney(montantA);
            machine.insertMoney(montantB);
            machine.insertMoney(montantC);  
            machine.refund();
            assertEquals("Rendre la monnaie ne remet pas correctement la balance à zéro",0,machine.getBalance());
        }
        
        @Test (expected = IllegalArgumentException.class)
        //S9 : On ne peut pas insérer de montant négatif
        public void cantInsertNegativeAmount(){
            machine.insertMoney(-10);
        }
        
        @Test (expected = IllegalArgumentException.class)
        //S10 : On ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
        public void cantInitializeWithNegativePrice(){
            TicketMachine machineIncorrecte = new TicketMachine(-10);
        }
        
}
