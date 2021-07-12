# Vending Machine 

## The statement of the problem

Un client doreste sa ii cream soft pentru o masina care vinde sucuri, acesta doareste ca masina sa primesca diferite bancnote 1,5,10 si monede de 10 si 50 de bani
oferind clientilor rest, in cazul in care nu are fonduri pentru a da rest sa afiseze pe ecran acest lucru si sa ceara confirmare de la utilizator daca
este sigur ca doreste sa cumpere chiar daca nu ii da rest daca nu accepta aparatul ii va returna banii si va reveni in starea initiala.
Odata selectat un produs aparatul trebuie sa dea rest doar daca suma ramasa este mai mica decat pretul oricarui produs in caz contrar va astepta ca,
clientul sa apese pe butonul rest.

Interactiunea cu acesta masina se va face de la tastatura ( sdin )

**Cerinte:**
1. aceasta sa poata vinde minim 5 tipuri de produse diferite
2. sa poata da rest in numar minim de bacnote si/sau monede in functie de cate monede/bacnote are
3. se doreste implementare design paternului Singleton pentru instanta masinii
4. sa fie implementate design paternurile: Factory method si builder
5. respectarea principiilor OOP
6. validare inputului si outputului
7. utilizatorul sa nu poata introduce o suma mai mare de 50 lei, in caz contrar sa ii dea bani inapoi cu un mesaj

**Restrictii:**
1. NU ESTE VOIE CU LOMBOK DECAT PENTRU Getters, Setters, NoArgsConstructor si toString
2. java versiunea 15/11 ( sa devina lucrurile mai interesante )
