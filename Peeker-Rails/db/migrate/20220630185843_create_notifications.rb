# frozen_string_literal: true

class CreateNotifications < ActiveRecord::Migration[7.0]
  def change
    create_table :notifications, id: :uuid do |t|
      t.string :heading
      t.string :subtitle
      t.string :content
      t.jsonb :data
      t.integer :type
      t.references :user, null: false, foreign_key: true, type: :uuid

      t.timestamps
    end
  end
end
