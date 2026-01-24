package org.catrobat.catroid.modloader

data class LuaBrickDefinition(
    val name: String,
    val category: String,
    val codeToExecute: String,
    val parameters: List<String> = emptyList(), // Ex: ['passos', 'duracao']
    val colorHex: String = "#FFFFFF" // Cor da categoria
)

object LuaBrickRegistry {
    private val registeredBricks = mutableListOf<LuaBrickDefinition>()

    fun register(name: String, category: String, code: String, parameters: List<String>, color: String) {
        // Evitar duplicatas
        registeredBricks.removeAll { it.name == name }
        
        val def = LuaBrickDefinition(name, category, code, parameters, color)
        registeredBricks.add(def)
    }

    fun getAll(): List<LuaBrickDefinition> = registeredBricks
    
    fun getByName(name: String): LuaBrickDefinition? = registeredBricks.find { it.name == name }
}
