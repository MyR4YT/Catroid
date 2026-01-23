package org.catrobat.catroid.modloader

data class LuaBrickDefinition(
    val name: String,
    val category: String, // 'Looks', 'Motion', 'Sound', etc.
    val codeToExecute: String, // O nome da função Lua ou o código em si
    val iconResId: Int = 0 // Placeholder para ícone
)

object LuaBrickRegistry {
    private val registeredBricks = mutableListOf<LuaBrickDefinition>()

    fun register(name: String, category: String, code: String) {
        val def = LuaBrickDefinition(name, category, code)
        registeredBricks.add(def)
        // Aqui, num cenário real, você notificaria o BrickCategoryAdapter para atualizar a UI
        // e inserir o novo bloco na lista de seleção.
        android.util.Log.d("LuaBrickRegistry", "Novo bloco registrado: $name na categoria $category")
    }

    fun getAll(): List<LuaBrickDefinition> = registeredBricks

    fun getByName(name: String): LuaBrickDefinition? {
        return registeredBricks.find { it.name == name }
    }
}
