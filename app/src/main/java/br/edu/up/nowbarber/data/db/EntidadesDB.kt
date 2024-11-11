

package br.edu.up.nowbarber.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.up.nowbarber.data.models.Barbearia
import br.edu.up.nowbarber.data.models.BarbeariaDao
import br.edu.up.nowbarber.data.models.Servico
import br.edu.up.nowbarber.data.models.ServicoDao
import br.edu.up.nowbarber.data.models.Cliente
import br.edu.up.nowbarber.data.models.ClienteDao
// Importar outras entidades e DAOs conforme necessário

// Lista todas as entidades do banco de dados na anotação @Database
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
    abstract fun getClienteDao(): ClienteDao
    // Adicione métodos para outros DAOs aqui
}

