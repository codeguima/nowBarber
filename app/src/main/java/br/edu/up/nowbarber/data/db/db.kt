

package br.edu.up.nowbarber.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.models.BarbeariaDao
import br.edu.up.nowbarber.data.models.ServicoDao
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.models.ClienteDao
import br.edu.up.nowbarber.data.models.Servico


@Database(
    entities = [
        Barbearia::class,
        Servico::class,
        Cliente::class,
        // Adicione novas entidades aqui conforme necessário
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBarbeariaDao(): BarbeariaDao
    abstract fun getServicoDao(): ServicoDao
    abstract fun getClienteoDao(): ClienteDao
    // Adicione métodos para outros DAOs aqui
}


// 4) Abrir o banco de dados
fun abrirBanco(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        name = "arquivo.db"
    ).build()
}
