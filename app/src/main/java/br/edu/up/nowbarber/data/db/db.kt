

package br.edu.up.nowbarber.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.edu.up.nowbarber.data.models.Agendamento
import br.edu.up.nowbarber.data.models.AgendamentoDao
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
        Agendamento::class
        // Adicione novas entidades aqui conforme necessário
    ],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class, GeoPointConverter::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getBarbeariaDao(): BarbeariaDao
    abstract fun getServicoDao(): ServicoDao
    abstract fun getClienteDao(): ClienteDao
    abstract fun getAgendamentoDao(): AgendamentoDao
    // Adicione métodos para outros DAOs aqui
}


// 4) Abrir o banco de dados
fun abrirBanco(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        name = "arquivo.db"
    ).fallbackToDestructiveMigration()//sso apagará todos os dados do banco de dados sempre que houver uma alteração no esquema.
        .build()
}
