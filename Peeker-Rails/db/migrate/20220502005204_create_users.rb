# frozen_string_literal: true

class CreateUsers < ActiveRecord::Migration[7.0]
  def change
    create_table :users, id: :uuid do |t|
      t.string :name
      t.string :last_name
      t.string :email,           null: false
      t.string :password_digest, null: false
      t.boolean :verified, null: false, default: false
      t.boolean :notifications_enabled, null: false, default: true
      t.integer :days_about_to_expire, null: false, default: 30

      t.timestamps
    end

    add_index :users, :email, unique: true
  end
end
