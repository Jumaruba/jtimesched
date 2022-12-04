# Assignment 9 - Mutation Testing

<!-- - Mutation score of the unit tests you developed in all assignments.
    Adicionalmente dizer que mudamos o codigo de testes anteriores para não haver testes a falhar?
        - ProjectSerializer :: unnamedElementTest e nullElementTest() :: falhavam porque addXmlElement nao lidava com strings nulas ou vazias no element. Adicionamos um if que verifica isto e dá SAXException se falhar
        - ProjectTime :: parseSecondsInvalidTest :: adicionamos um if para dar throw de ParseException se o parameter for null
        - ProjectTime :: formatSecondsBoundary e formatSecondsPartitionTest :: devolvemos 0:00:00 para numeros negativos
    Explicar porque razão não incluímos os testes e a class JTimeSched
- Equivalent mutants, if any.
- Brief description of test cases developed to increase project’s mutation score. -->

