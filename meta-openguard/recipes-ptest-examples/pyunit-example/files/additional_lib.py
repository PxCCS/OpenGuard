class AdditionalLib():
    __localVar = "Not Set"
    __anotherVar = 1
    
    def __init__(self):
        self.__localVar = "Set"

    def GetLocalVar(self):
        return self.__localVar

    def GetVar(self):
        return self.__anotherVar
