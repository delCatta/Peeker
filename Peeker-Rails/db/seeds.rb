# frozen_string_literal: true

# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the bin/rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: "Star Wars" }, { name: "Lord of the Rings" }])
#   Character.create(name: "Luke", movie: movies.first)

User.create(name: 'Diego', last_name: 'Cattarinich Clavel', email: 'd@c.c', password: '123456',
            password_confirmation: '123456', verified: true)
User.create(name: 'Diego', last_name: 'Quezada', email: 'd@q.q', password: '123456',
            password_confirmation: '123456', verified: true)
User.create(name: 'Benja', last_name: 'Sanchez', email: 'b@s.s', password: '123456',
            password_confirmation: '123456', verified: true)
User.create(name: 'Bruno', last_name: 'Prieto', email: 'b@p.p', password: '123456',
            password_confirmation: '123456', verified: true)

User.all.each do |user|
  next if user.last_name == 'Quezada'

  Document.create(
    name: "Pasaporte de #{user.name} #{user.last_name}",
    description: 'Este es mi pasaporte Chileno.',
    document_type: 0,
    expiration_date: DateTime.now + rand(1100).days,
    emission_date: DateTime.now - rand(1100).days,
    user_id: user.id
  )
  Document.create(
    name: "Cédula de #{user.name} #{user.last_name}",
    description: 'Esta es mi cedula de identidad.',
    document_type: 1,
    expiration_date: DateTime.now + rand(1100).days,
    emission_date: DateTime.now - rand(1100).days,
    user_id: user.id
  )
end
