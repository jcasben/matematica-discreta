import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Jesús Castillo Benito
 * - Nom 2: Marc Link Cladera
 * - Nom 3: Unai Pueyo Almodovar
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
   * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
     */
    static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      for (int x : universe) {
        boolean found = false;
        for (int y : universe) {
          if(p.test(x) && !q.test(x,y)) {
            continue;
          }
          if (found) return false;
          found = true;
        }
        if (!found) return false;
      }
      return true; // TO DO
    }

    /*
     * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      boolean found = false;
      for (int x : universe) {
        boolean incorrect = false;
        for (int y : universe) {
          if(p.test(y) && !q.test(x,y)) {
            incorrect = true;
            break;
          }
        }
        if (found && !incorrect) return false;
        if(!incorrect) found = true;
      }
      if (!found) return false;
      return true;
    }

    /*
     * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
     */
    static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      boolean found = false;
      for (int x : universe) {
        for (int y : universe) {
          for (int z : universe) {
            if ((!p.test(x, z) && !q.test(y, z)) || (p.test(x, z) && q.test(y, z))) {
              break;
            }
            found = true;
          }
        }
      }
      if(!found) return false;
      return true;
    }

    /*
     * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
     */
    static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
      boolean testP = true;
      boolean testQ = true;
      for (int x : universe) {
        if (!p.test(x)) testP = false;
        if (!q.test(x)) testQ = false;
      }
      if (testP && !testQ) return false;
      return true;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x ∃!y. P(x) -> Q(x,y) ?

      assertThat(
              exercici1(
                      new int[] { 2, 3, 5, 6 },
                      x -> x != 4,
                      (x, y) -> x == y
              )
      );

      assertThat(
              !exercici1(
                      new int[] { -2, -1, 0, 1, 2, 3 },
                      x -> x != 0,
                      (x, y) -> x * y == 1
              )
      );

      // Exercici 2
      // ∃!x ∀y. P(y) -> Q(x,y) ?

      assertThat(
              exercici2(
                      new int[] { -1, 1, 2, 3, 4 },
                      y -> y <= 0,
                      (x, y) -> x == -y
              )
      );

      assertThat(
              !exercici2(
                      new int[] { -2, -1, 1, 2, 3, 4 },
                      y -> y < 0,
                      (x, y) -> x * y == 1
              )
      );

      // Exercici 3
      // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

      assertThat(
              exercici3(
                      new int[] { 2, 3, 4, 5, 6, 7, 8 },
                      (x, z) -> z % x == 0,
                      (y, z) -> z % y == 1
              )
      );

      assertThat(
              !exercici3(
                      new int[] { 2, 3 },
                      (x, z) -> z % x == 1,
                      (y, z) -> z % y == 1
              )
      );

      // Exercici 4
      // (∀x. P(x)) -> (∀x. Q(x)) ?

      assertThat(
              exercici4(
                      new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
                      x -> x % 2 == 0, // x és múltiple de 2
                      x -> x % 4 == 0 // x és múltiple de 4
              )

      );

      assertThat(
              !exercici4(
                      new int[] { 0, 2, 4, 6, 8, 16 },
                      x -> x % 2 == 0, // x és múltiple de 2
                      x -> x % 4 == 0 // x és múltiple de 4
              )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
   * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
   * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
   */
  static class Tema2 {
    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] rel) {
      /*
       * Para que sea de equivalencia ha de ser reflexiva, simétrica y transitiva.
       */

      boolean reflexiva = reflexiva(a, rel);
      boolean simetrica = simetrica(rel);
      boolean transitiva = transitiva(rel);

      return reflexiva && simetrica && transitiva;
    }

    /*
     * Para comprobar que es reflexiva ha de cumplir:
     *  ∀a : a R a
     */
    private static boolean reflexiva(int[] a, int[][] rel) {
      for (int valoresa : a) {
        boolean valido = false;

        for (int[] ints : rel) {
          if ((ints[0] == valoresa) && (ints[1] == valoresa)) {
            valido = true;
            break;
          }
        }
        if (!valido) return false;
      }
      return true;
    }

    /*
     * Para comprobar que es simetrica ha de cumplir:
     *  ∀a,b : a R b → b R a
     */
    private static boolean simetrica(int[][] rel) {

      for (int[] ab : rel) {
        boolean existeba = false;
        for (int[] ba : rel) {
          if(ab[0] == ba[1] && ab[1] == ba[0]) existeba = true;
        }
        if(!existeba) return false;
      }

      return true;

    }

    /*
     * Para comprobar que es transitiva ha de cumplir:
     *  ∀a, b, c : a R b ∧ b R c → a R c
     */
    private static boolean transitiva(int[][] rel) {
      int [] aux = new int[2];
      for (int[] e1 : rel) {
        for (int[] e2 : rel) {
          if(e1[1] == e2[0]){
            boolean existe = false;
            aux[0] = e1[0];
            aux[1] = e2[1];
            for (int[] ints : rel) {
              if(Arrays.equals(ints,aux)) {
                existe = true;
              }
            }
            if (!existe) return false;
          }
        }
      }
      return true;
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
     * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static int exercici2(int[] a, int[][] rel) {
      /*
       * Lo primero de todo es comprobar si es una relacion de equivalencia
       */
      if (!reflexiva(a,rel) || !simetrica(rel) || !transitiva(rel)) {
        return -1;
      }

      /*
       * Creamos una array de hasSet que posteriormente añadiremos cada set a un Set que al apliocar
       * el metodo '.lenght' nos dara las clases de equivalencia
       */
      Set<Integer>[] clasesEq = new HashSet[a.length];

      for (int pos = 0; pos < clasesEq.length; pos++) {
        clasesEq[pos] = new HashSet<>();
        clasesEq[pos].add(a[pos]); //añadimos cada valor de 'a' al a cada set
      }

      for (int i : a) {
        for (int[] ints : rel) {
          if(ints[0] == i) {
            clasesEq[i].add(ints[1]);
          }
          if(ints[1] == i) {
            clasesEq[i].add(ints[0]);
          }
        }
      }

      /*
       * Para unirlos y eliminar los sets repetidos en la array
       */
      Set<Set<Integer>> setsOfSets = new HashSet<>();
      for (Set<Integer> clases : clasesEq) {
        setsOfSets.add(clases);
      }

      return setsOfSets.size();
    }

    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     *
     */
    static int contadora = 0;
    static boolean exercici3(int[] a, int[] b, int[][] rel) {

      /*
       * Con este for confirmamos que los pares son correctos.
       */
      for (int[] ints : rel) {
        if(!perteneceAB(ints[0], ints[1],a,b)) {
          return false;
        }
      }

      /*
       * Con este for comprobamos que no salen dos relaciones de el mismo a o que no sale ninguna
       * de un elemento de a
       */
      for (int num : a) {
        int cont = 0;
        for (int[] ints : rel) {
          if(ints[0] == num){
            cont++;
          }
        }
        if(cont != 1) {
          return false;
        }
      }

      //Si llega hasta este punto podemos hacer return de true
      return true;

    }
    private static boolean perteneceAB(int vala, int valb, int[] a, int[] b) {
      boolean pertenecea = false;
      boolean perteneceb = false;
      for (int num : a) {
        if (num == vala) {
          pertenecea = true;
          break;
        }
      }
      for (int num : b) {
        if (num == valb) {
          perteneceb = true;
          break;
        }
      }
      return pertenecea && perteneceb;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
     * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de `codom`.
     * - En qualsevol altre cas, retornau 0.
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
     */
    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {

      boolean exaustiva = true;
      boolean inyectiva = true;
      int freqMax = 1;
      int[] imagen = new int[dom.length];

      /*
       * Miramos si es exaustiva:
       * Si el dominio de llegada ('codom') es mayor al elemento de salida ('dom')
       */

      if (dom.length < codom.length) exaustiva = false;

      if(exaustiva){

        for (int i = 0; i < dom.length; i++) {
          imagen[i] = f.apply(dom[i]);
        }

        for (int c : codom) {
          boolean existe = false;
          for (int i : imagen) {
            if(c == i){
              existe = true;
            }
          }
          if(!existe){
            exaustiva = false;
            break;
          }
        }

        /*
         * Buscamos la frecuancia máxima, para ello primero ordenamos la array de imagenes
         */
        int contadorElemAct = 1;

        Arrays.sort(imagen);
        for (int i = 1; i < imagen.length; i++) {
          if (imagen[i] == imagen[i - 1])
            contadorElemAct++;
          else
            contadorElemAct = 1;

          if (contadorElemAct > freqMax) {
            freqMax = contadorElemAct;

          }
        }
      }

      /*
       * Miramos si es inyectiva
       * Si f (a) = f (a'), entonces a = a'
       */
      for (int a : dom) {
        for (int aPrima : dom) {
          if((f.apply(a).equals(f.apply(aPrima))) && (a != aPrima)){
            inyectiva = false;
          }
        }
      }
      /*
       * Añadimos las imagenes a un set para ver cuantas imagenes diferentes tenemos
       */
      Set<Integer> imagenes = new HashSet<>();
      for (int elemento : dom) {
        imagenes.add(f.apply(elemento));
      }

      if(exaustiva){
        return freqMax;
      } else if (inyectiva){
        return imagenes.size() - codom.length;
      }else {
        return 0;
      }
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `rel` és d'equivalencia?

      assertThat(
              exercici1(
                      new int[] { 0, 1, 2, 3},
                      new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
              )
      );

      assertThat(
              !exercici1(
                      new int[] { 0, 1, 2, 3 },
                      new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
              )
      );

      // Exercici 2
      // si `rel` és d'equivalència, quants d'elements té el seu quocient?

      final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(
              exercici2(
                      int09,
                      generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
              )
                      == 3
      );

      assertThat(
              exercici2(
                      new int[] { 1, 2, 3 },
                      new int[][] { {1, 1}, {2, 2} }
              )
                      == -1
      );

      // Exercici 3
      // `rel` és una funció?

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(
              exercici3(
                      int05,
                      int09,
                      generateRel(int05, int09, (x, y) -> x == y)
              )
      );

      assertThat(
          !exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y/2)
          )
      );

      // Exercici 4
      // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
      // sino, |im f| - |codom| si és injectiva
      // sino, 0

      assertThat(
          exercici4(
            int09,
            int05,
            x -> x / 4
          )
          == 0
      );

      assertThat(
          exercici4(
            int05,
            int09,
            x -> x + 3
          )
          == int05.length - int09.length
      );

      assertThat(
          exercici4(
            int05,
            int05,
            x -> (x + 3) % 6
          )
          == 1
      );
    }

    /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      ArrayList<int[]> rel = new ArrayList<>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
   * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
   * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
   *
   *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
   *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
   *
   * Podeu suposar que cap dels grafs té llaços.
   */
  static class Tema3 {
    /*
     * Retornau l'ordre menys la mida del graf (no dirigit).
     */
    static int exercici1(int[][] g) {
      int order = g.length;  //Cantidad de vértices
      int size = 0;   //Cantidad de aristas

      //Aplicamos el lema de las manos encajadas [Sumatorio aristas de v = 2 * E]
      for (int[] nodos : g) size += nodos.length;
      size /= 2;

      return order - size;
    }

    /*
     * Suposau que el graf (no dirigit) és connex. És bipartit?
     */
    static boolean exercici2(int[][] graph) {
      boolean[] visited = new boolean[graph.length];
      int[] colors = new int[graph.length];

      //En vez de una función recurrente hemos optado por usar una cola
      //con los siguientes nodos a comprobar
      List<Integer> nextNodes = new ArrayList<>();

      for (int i = 0; i < graph.length; i++) {
        //Si ya hemos comprobado este nodo, lo saltamos
        if (visited[i]) continue;

        //Marcamos como visitado el nodo y le asignamos un color
        visited[i] = true;
        nextNodes.add(i);
        colors[i] = 1;

        //Recorremos todos los posibles hijos del nodo usando nuestro stack
        while (!nextNodes.isEmpty()) {
          int currentNode = nextNodes.get(0);
          nextNodes.remove(0);

          //Obtenemos el color del nodo y el que deberían tener sus hijos
          int currentColor = colors[currentNode];
          int childColor = currentColor == 1 ? -1 : 1;

          //Comprobamos si cada hijo tiene su color correspondiente
          for (int node : graph[currentNode]) {
            if (visited[node]) {
              if (currentColor == colors[node]) return false;
            }else {
              nextNodes.add(node);
              colors[node] = childColor;
              visited[node] = true;
            }
          }
        }

      }

      return true;
    }

    /*
     * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
     * vèrtex i-èssim.
     */
    static int exercici3(int[][] g, int i) {
      return nodosGrado0(g, i, new boolean[g.length]);
    }

    /**
     * Función recursiva para saber la cantidad de nodos con grado 0.
     * Para poder hacer funcionar la función has de introducir el grafo inicial
     * {@param grafo}, el nodo de comienzo, {@param nodo}, y una matriz con N
     * elementos 'false' iguales a la cantidad de nodos de la matriz.
     *
     * @param grafo grafo original
     * @param nodo nodo de comienzo
     * @param nodosVisitados matriz con N elementos false iguales al número de vértices
     * @return la cantidad de nodos con un descendiente de grado 0
     */
    private static int nodosGrado0(int[][] grafo, int nodo, boolean[] nodosVisitados) {
      //Hemos encontrado un nodo con grado 0. Fin de la recursión
      if (grafo[nodo].length == 0) {
        //Si ya hemos visitado a este hijo, no lo tenemos en cuenta
        if (nodosVisitados[nodo]) return 0;

        //Si no, lo activamos como visto
        nodosVisitados[nodo] = true;
        return 1;
      }

      int nodosGrado0 = 0;

      for (int elemento : grafo[nodo])
        nodosGrado0 += nodosGrado0(grafo, elemento, nodosVisitados);

      return nodosGrado0;
    }

    /*
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
     * del graf subjacent. Suposau que totes les arestes tenen pes 1.
     */
    static int exercici4(int[][] g) {
      //Llama a la función recursiva para buscar el diámetro
      return calculateDiameter(0, g);
    }

    /**
     * Calcula el diámetro de un árbol dirigido mirando recursivamente todos sus nodos.
     *
     * @param nodo nodo inicial
     * @param graph grafo de búsqueda
     * @return el diámetro del grafo
     */
    private static int calculateDiameter(int nodo, int[][] graph) {
      if (graph[nodo].length == 0) return 1;

      int diameter = 0;

      for (int element : graph[nodo]) {
        int childDiameter =  calculateDiameter(element, graph);
        if (childDiameter + diameter > diameter) diameter +=  childDiameter;
      }

      return diameter;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      final int[][] undirectedK6 = {
        { 1, 2, 3, 4, 5 },
        { 0, 2, 3, 4, 5 },
        { 0, 1, 3, 4, 5 },
        { 0, 1, 2, 4, 5 },
        { 0, 1, 2, 3, 5 },
        { 0, 1, 2, 3, 4 },
      };

      /*
         1
      4  0  2
         3
      */
      final int[][] undirectedW4 = {
        { 1, 2, 3, 4 },
        { 0, 2, 4 },
        { 0, 1, 3 },
        { 0, 2, 4 },
        { 0, 1, 3 },
      };

      // 0, 1, 2 | 3, 4
      final int[][] undirectedK23 = {
        { 3, 4 },
        { 3, 4 },
        { 3, 4 },
        { 0, 1, 2 },
        { 0, 1, 2 },
      };

      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
      final int[][] directedG1 = {
        { 1, 2 }, // 0
        { 3 },    // 1
        { 3, 8 }, // 2
        { 4 },    // 3
        { 5, 6 }, // 4
        {},       // 5
        {},       // 6
        { 0 },    // 7
        {},
      };


      /*
              0
         1    2     3
            4   5   6
           7 8
      */

      final int[][] directedRTree1 = {
        { 1, 2, 3 }, // 0 = r
        {},          // 1
        { 4, 5 },    // 2
        { 6 },       // 3
        { 7, 8 },    // 4
        {},          // 5
        {},          // 6
        {},          // 7
        {},          // 8
      };

      /*
            0
            1
         2     3
             4   5
                6  7
      */

      final int[][] directedRTree2 = {
        { 1 },
        { 2, 3 },
        {},
        { 4, 5 },
        {},
        { 6, 7 },
        {},
        {},
      };

      assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
      assertThat(exercici1(undirectedW4) == 5 - 2*4);

      assertThat(exercici2(undirectedK23));
      assertThat(!exercici2(undirectedK6));

      assertThat(exercici3(directedG1, 0) == 3);
      assertThat(exercici3(directedRTree1, 2) == 3);

      assertThat(exercici4(directedRTree1) == 5);
      assertThat(exercici4(directedRTree2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * Per calcular residus podeu utilitzar l'operador %, però anau alerta amb els signes.
   * Podeu suposar que cada vegada que se menciona un mòdul, és major que 1.
   */
  static class Tema4 {
    /*
     * Donau la solució de l'equació
     *
     *   ax ≡ b (mod n),
     *
     * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu suposar que n > 1.
     *
     * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici1(int a, int b, int n) {
      int d = mcd(a,n);
      if(b % d != 0) return null;
      int [] xy = euclides(n,a);

      int sol = xy[1];
      int k = n/d;

      //Si n/mcd(a,n) es < 0, le cambiamos el signo.
      if(k < 0) k *= -1;

      /*
       * Mientras la x < 0, le sumaremos el n/mcd(a,n)
       */
      while (sol < 0) {
        sol += k;
      }
      return new int[] { sol, k };
    }

    /**
     * Método recursivo para calcular el mcd de 2 números
     * @param a número 1
     * @param b número 2
     * @return mcd del número 1 y número 2
     */
    private static int mcd(int a, int b) {
      if(a%b==0){
        return b;
      }else{
        return mcd(b,a%b);
      }
    }

    /**
     * Implementación del algoritmo de Euclides.
     * @param ni módulo
     * @param ai a
     * @return array donde la posición 0 es la x y la posición 1 es la y
     */
    private static int[] euclides(int ni, int ai) {

      boolean neg = false;

      /*
       * Como el algoritmo de Euclides no se aplica con números negativos, primero miramos si `ai` es negativo. Si lo es,
       * lo multiplicaremos por -1 para dejarlo positivo. Además, activaremos el boolean `neg` para que al final de la
       * ejecución se devuelva el signo negativo a la `y`, que es el número relacionado a la `ai`.
       */
      if (ai < 0) {
        ai *= -1;
        neg = true;
      }

      int [] n = {ni,0,1,0};
      int [] a = {ai,0,0,1};
      int [] aux;

      /*
       * Mientras el residuo de la división de `ni` y `ai` sea distinto a 0, realizaremos este proceso.
       */
      while (n[0] % a[0] != 0) {
        a[1] = n[0] / a[0];
        aux = Arrays.copyOf(a,4);
        a[0] = n[0] % a[0];
        a[2] = n[2] - a[2] * a[1];
        a[3] = n[3] - a[3] * a[1];
        n = Arrays.copyOf(aux,4);
      }
      //Le devolvemos el signo negativo al factor `y`
      if (neg) {
        a[3] *= -1;
      }
      return new int[] {a[2], a[3]};
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { x ≡ b[0] (mod n[0])
     *  { x ≡ b[1] (mod n[1])
     *  { x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També podeu suposar
     * que els dos arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2a(int[] b, int[] n) {
      //Inicializacion de los arrays que contendrán los valores P, Q y la solución de las ecuaciones.
      int [] p = new int[n.length];
      int [] q = new int[n.length];
      int [] sol = { 0, 1 };
      Arrays.fill(p,1);

      for (int i = 0; i < n.length; i++) {
        for (int j = 0; j < n.length; j++) {
          if (i == j) {
            continue;
          }
          /*
           * Para comprobar si el sistema de ecuaciones tiene solución, miramos si alguna pareja de módulos no es
           * coprima
           */
          if (mcd(n[i],n[j]) != 1) return null;
          //Utilizamos el bucle que recorre el array de modulos para calcular la ` P `
          p[i] *= n[j];
        }
        //Calculamos la ` m `
        sol[1] *= n[i];
        //Utilizamos el algoritmo de Euclides para calcular la ` Q `
        int [] aux = euclides(n[i],(p[i] % n[i]));
        q[i] = aux[1];
        //Calculamos la solución utilizando la formula: x = P1*Q1*a1 + P2*Q2*a2 + ... + Pn*Qn*an
        sol[0] += p[i]*q[i]*b[i];
      }

      //Si la solución es < 0, le sumamos la ` m ` hasta que sea positivo
      while(sol[0] < 0) {
        sol[0] += sol[1];
      }
      return sol;
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { a[0]·x ≡ b[0] (mod n[0])
     *  { a[1]·x ≡ b[1] (mod n[1])
     *  { a[2]·x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que n[i] > 1. També
     * podeu suposar que els tres arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2b(int[] a, int[] b, int[] n) {
      int [] nb = new int[b.length];
      int [] nn = new int[n.length];
      int [] aux;
      for (int i = 0; i < a.length; i++) {
        aux = transformToXB(a[i], b[i], n[i]);
        if (aux == null) return null;
        nb[i] = aux[0];
        nn[i] = aux[1];
      }
      int [] sol = exercici2a(nb,nn);
      if (sol == null) return null;

      return sol;
    }

    static int[] transformToXB(int a, int b, int n) {
      int d = mcd(a,n);
      if(b % d != 0) return null;
      int [] xy = euclides(n,a);

      int sol = xy[1];
      int k = n/d;

      //Si n/mcd(a,n) es < 0, le cambiamos el signo.
      if(k < 0) k *= -1;

      //Se calcula la b/mcd(a,n), si este da negativo, se cambia a positivo.
      int k2 = (b/d);
      if (k2 < 0) k2 *= -1;
      sol *= k2;

      return new int[] { sol, k };
    }

    /*
     * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers, ordenada de menor a
     * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
     *
     * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
     *
     * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar la força bruta
     * (el que coneixeu com el mètode manual d'anar provant).
     */
    static ArrayList<Integer> exercici3a(int n) {
      return primeDescomposition(n); // TO DO
    }

    /**
     * Calcula la descomposición en números primos de un número
     * @param n número a descomponer
     * @return factores primos en los que se ha descompuesto el número
     */
    public static ArrayList<Integer> primeDescomposition(int n) {
      //Aquí se guardará la descomposición del número.
      ArrayList <Integer> descomposition = new ArrayList<>();
      /*
      * Se empieza la descomposición a partir del 2, pero si el número que se introduce es un 1, se devolverá
      * directamente el 1.
      */
      int factor = 2;
      if(n == 1) {
        descomposition.add(n);
      } else {
        /*
        * Mientras el número sea mayor o igual que el factor primo, se añadirá este factor a la descomposición y se
        * dividirá el número por ese factor.
        */
        while (factor <= n) {
          while (n % factor == 0) {
            descomposition.add(factor);
            n /= factor;
          }
          factor++;
        }
      }
      return descomposition;
    }

    /*
     * Retornau el nombre d'elements invertibles a Z mòdul n³.
     *
     * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però n³ no té perquè.
     * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
     *
     * No podeu utilitzar `long` per solucionar aquest problema. Necessitareu l'exercici 3a.
     * No, tampoc podeu utilitzar `double`.
     */
    static int exercici3b(int n) {
      ArrayList<Integer> nDescomposition = primeDescomposition(n);

      //Se almacenan los elementos únicos de la factorización en una lista.
      List<Integer> indexes = new ArrayList<>(new HashSet<>(nDescomposition));

      //Contamos cuantas veces aparece cada índice
      int[] count = new int[indexes.size()];
      for (Integer integer : nDescomposition) count[indexes.indexOf(integer)]++;

      int fiEuler = 1;

      //Calculamos la fi de Euler
      for (int i = 0; i < count.length; i++) {
        count[i] *= 3;
        fiEuler *= pow(indexes.get(i), count[i]) - pow(indexes.get(i), count[i] - 1);
      }

      return fiEuler;
    }

    /**
     * Calcula la potencia de el número dado elevado al exponente.
     *
     * @param number numero a elevar
     * @param exponent exponente
     * @return la potencia
     */
    private static int pow(int number, int exponent) {
      int result = number;

      for (int i = 0; i < exponent - 1; i++) result *= number;

      return result;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
      assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
      assertThat(exercici1(2, 3, 6) == null);

      assertThat(
        exercici2a(
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
              Arrays.equals(
                      exercici2a(
                              new int[] { 3, -1, 2 },
                              new int[] { 5,  8, 9 }
                      ),
                      new int[] { 263, 360 }
              )
      );

      assertThat(
              exercici2b(
                      new int[] { 1, 1 },
                      new int[] { 1, 0 },
                      new int[] { 2, 4 }
              )
                      == null
      );

      assertThat(
              Arrays.equals(
                      exercici2b(
                              new int[] { 2,  -1, 5 },
                              new int[] { 6,   1, 1 },
                              new int[] { 10,  8, 9 }
                      ),
                      new int[] { 263, 360 }
              )
      );

      assertThat(exercici3a(10).equals(List.of(2, 5)));
      assertThat(exercici3a(1291).equals(List.of(1291)));
      assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19 )));

      assertThat(exercici3b(10) == 400);

      // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense calcular n³.
      assertThat(exercici3b(1292) == 961_496_064);

      // Aquest exemple té el resultat fora de rang
      //assertThat(exercici3b(1291) == 2_150_018_490);
    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
